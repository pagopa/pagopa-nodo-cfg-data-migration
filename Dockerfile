#
# Build
#
FROM maven:3.8.4-openjdk-17-slim as buildtime
WORKDIR /build
COPY . .
RUN mvn clean package


FROM eclipse-temurin:17-jre as builder
WORKDIR app
COPY --from=buildtime /build/target/*.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract


FROM ghcr.io/pagopa/docker-base-springboot-openjdk17:v1.1.0
WORKDIR app
ADD --chown=spring:spring https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.25.1/opentelemetry-javaagent.jar .
COPY --chown=spring:spring  --from=builder app/dependencies/ ./
COPY --chown=spring:spring  --from=builder app/snapshot-dependencies/ ./
# https://github.com/moby/moby/issues/37965#issuecomment-426853382
RUN true
COPY --chown=spring:spring  --from=builder app/spring-boot-loader/ ./
COPY --chown=spring:spring  --from=builder app/application/ ./

EXPOSE 8080

ENTRYPOINT ["java","-javaagent:opentelemetry-javaagent.jar","--enable-preview","org.springframework.boot.loader.JarLauncher"]
