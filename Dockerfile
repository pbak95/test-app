FROM adoptopenjdk:latest as builder
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
RUN java -Djarmode=layertools -jar app.jar extract


FROM adoptopenjdk:latest
RUN addgroup --system app && adduser --system --ingroup app app

USER app
VOLUME /tmp
WORKDIR /app

COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./

COPY opentelemetry-javaagent.jar ./opentelemetry-javaagent.jar

ENV OTEL_TRACES_EXPORTER=logging OTEL_METRICS_EXPORTER=logging OTEL_LOGS_EXPORTER=logging


ENTRYPOINT java -Dotel.metrics.exporter=none -Dotel.traces.exporter=logging -Dotel.metrics.exporter=logging -Dotel.logs.exporter=logging -Dotel.exporter.otlp.endpoint=http://otel-collector.opensearch.svc.cluster.local:4317 -Dotel.javaagent.debug=true -javaagent:opentelemetry-javaagent.jar org.springframework.boot.loader.JarLauncher
