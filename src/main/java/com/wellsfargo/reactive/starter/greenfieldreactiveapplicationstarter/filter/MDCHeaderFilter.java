package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Component
public class MDCHeaderFilter implements WebFilter {
    private static final String MDC_HEADER_PREFIX = "MDC-";
    public static final String CONTEXT_MAP = "context-map";

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain chain) {
        serverWebExchange.getResponse()
                .beforeCommit(() -> addContextToHttpResponseHeaders(serverWebExchange.getResponse()));

        return chain.filter(serverWebExchange)
                .subscriberContext(ctx -> addRequestHeadersToContext(serverWebExchange.getRequest(), ctx));
    }

    private Context addRequestHeadersToContext(
            final ServerHttpRequest request,
            final Context context) {

        final Map<String, String> contextMap = request
                .getHeaders().toSingleValueMap().entrySet()
                .stream()
                .filter(x -> x.getKey().startsWith(MDC_HEADER_PREFIX))
                .collect(
                        toMap(v -> v.getKey().substring(MDC_HEADER_PREFIX.length()),
                                Map.Entry::getValue
                        )
                );

        return context.put(CONTEXT_MAP, contextMap);
    }

    private Mono<Void> addContextToHttpResponseHeaders(
            final ServerHttpResponse res) {

        return Mono.subscriberContext()
                .doOnNext(context -> {
                    if (!context.hasKey(CONTEXT_MAP)) return;

                    final HttpHeaders headers = res.getHeaders();
                    context.<Map<String, String>>get(CONTEXT_MAP)
                            .forEach((key, value) -> headers.add(MDC_HEADER_PREFIX + key, value));
                }).then();
    }
}