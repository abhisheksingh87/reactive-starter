# **greenfield-reactive-app-starter**


This **greenfield-reactive-app-starter** helps you to develop a spring boot microservice based on [**Reactive**](https://blog.redelastic.com/what-is-reactive-programming-bc9fa7f4a7fc#reactive) techniques.

We recommended the **Reactive** path for _greenfield applications_, to take advantage of the non-blocking behavior that improves application performance and resiliency.

The **Application Starter Cookbook** provides recipes to help complete the tasks required to build a fully functional spring boot microservice.

---
### Prerequisite

- JDK 8 or higher
- IntelliJ or Eclipse IDE
- GIT client
- Access to **Application Starter Cookbook**

---

### How to use this starter

1. Clone this repository

1. Build the starter microservice locally: `gradlew bootJar`
  
1. Run the starter microservice locally: `gradlew bootRun`

1. Verify microservice health in the browser
   - `http://localhost:8080/actuator/info`
   - `http://localhost:8080/actuator/health`
 
1. Use the recipes in the  **Application Starter Cookbook** to build a fully functional spring boot microservice for your business needs.

---

### Notes
- [What is Reactive Programming ?](https://blog.redelastic.com/what-is-reactive-programming-bc9fa7f4a7fc)
- [Essence of Reactive Programming](https://www.scnsoft.com/blog/java-reactive-programming)
