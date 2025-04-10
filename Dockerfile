FROM eclipse-temurin:17-jre-jammy

# Add non root user
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
RUN groupadd -r spring && useradd -r -g spring spring

WORKDIR /app

COPY build/libs/crm-0.0.1-SNAPSHOT.jar logs-service.jar

# Set ownership to non-root user
RUN chown -R spring:spring /app

USER spring

EXPOSE 8082

HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=5 \
    CMD curl -f http://localhost:8082/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "logs-service.jar"]