FROM eclipse-temurin:17-jre-jammy

# Install wget and other required tools
RUN apt-get update && apt-get install -y wget openssl && rm -rf /var/lib/apt/lists/*

# Create the certs directory
RUN mkdir -p /app/certs/

# Download the DocumentDB global bundle using wget
RUN wget https://truststore.pki.rds.amazonaws.com/global/global-bundle.pem -O /app/certs/global-bundle.pem

# Split the bundle into individual certs
RUN awk 'split_after == 1 {n++;split_after=0} /-----END CERTIFICATE-----/ {split_after=1}{print > "/app/certs/docdb-ca-" n ".pem"}' < /app/certs/global-bundle.pem

# Import each certificate into a custom truststore with password "password"
RUN \
  TRUSTSTORE=/app/certs/docdb-truststore.jks && \
  STOREPASS=password && \
  for CERT in /app/certs/docdb-ca-*; do \
    ALIAS=$(openssl x509 -noout -subject -in $CERT | sed -n 's/.*CN=//p' | tr -d ' /') || true; \
    if [ -z "$ALIAS" ]; then ALIAS=$(basename $CERT .pem); fi; \
    echo "Importing $ALIAS from $CERT" && \
    keytool -import -file $CERT -alias "$ALIAS" -storepass $STOREPASS -keystore $TRUSTSTORE -noprompt; \
  done && \
  rm -rf /app/certs/docdb-ca-*.pem


# Move truststore to a persistent location
RUN mkdir -p /app/certs && \
    mv /app/certs/docdb-truststore.jks /app/certs/


# Add non-root user
RUN groupadd -r spring && useradd -r -g spring spring

WORKDIR /app

COPY build/libs/crm-0.0.1-SNAPSHOT.jar logs-service.jar

# Set ownership to non-root user
RUN chown -R spring:spring /app/certs && chmod 644 /app/certs/docdb-truststore.jks


USER spring

EXPOSE 8082

HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=5 \
    CMD curl -f http://localhost:8082/actuator/health || exit 1

ENTRYPOINT ["java", "-Djavax.net.ssl.trustStore=/app/certs/docdb-truststore.jks", "-Djavax.net.ssl.trustStorePassword=password", "-jar", "logs-service.jar"]

