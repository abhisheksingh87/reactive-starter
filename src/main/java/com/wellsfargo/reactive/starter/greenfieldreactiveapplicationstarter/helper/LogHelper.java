package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.helper;

import org.slf4j.MDC;
import reactor.core.publisher.Signal;
import reactor.core.publisher.SignalType;
import reactor.util.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.filter.MDCHeaderFilter.CONTEXT_MAP;

public class LogHelper {

    public static <T> Consumer<Signal<T>> logOnNext(
            Consumer<T> log) {
        return signal -> {
            if (signal.getType() != SignalType.ON_NEXT) return;

            Optional<Map<String, String>> contextMap
                    = signal.getContext().getOrEmpty(CONTEXT_MAP);

            if (!contextMap.isPresent()) {
                log.accept(signal.get());
            } else {
                MDC.setContextMap(contextMap.get());
                try {
                    log.accept(signal.get());
                } finally {
                    MDC.clear();
                }
            }
        };
    }

    public static <T> Consumer<Signal<T>> logOnError(
            Consumer<Throwable> log) {
        return signal -> {
            if (!signal.isOnError()) return;

            Optional<Map<String, String>> contextMap
                    = signal.getContext()
                            .getOrEmpty(CONTEXT_MAP);

            if (!contextMap.isPresent()) {
                log.accept(signal.getThrowable());
            } else {
                MDC.setContextMap(contextMap.get());
                try {
                    log.accept(signal.getThrowable());
                } finally {
                    MDC.clear();
                }
            }
        };
    }

    public static Function<Context, Context> put(String key, String value) {
        return ctx -> {
            Optional<Map<String, String>> contextMap =
                    ctx.getOrEmpty(CONTEXT_MAP);

            if (contextMap.isPresent()) {
                contextMap.get().put(key, value);
                return ctx;
            } else {
                Map<String, String> ctxMap = new HashMap<>();
                ctxMap.put(key, value);

                return ctx.put(CONTEXT_MAP, ctxMap);
            }
        };
    }
}