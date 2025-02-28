#
# Build
#
FROM maven:3.8.4-openjdk-17-slim@sha256:150deb7b386bad685dcf0c781b9b9023a25896087b637c069a50c8019cab86f8 as buildtime
WORKDIR /build
COPY . .
RUN mvn clean package -Dmaven.test.skip=true


FROM eclipse-temurin:17-jre@sha256:5d86cdadfaf1f816502573b50ea89ea53ced31d5db9bb0aabc6fcf3f80905d5a as builder
WORKDIR app
COPY --from=buildtime /build/target/*.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract


FROM ghcr.io/pagopa/docker-base-springboot-openjdk17:v1.1.0@sha256:6fa320d452fa22066441f1ef292d15eb06f944bc8bca293e1a91ea460d30a613
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
