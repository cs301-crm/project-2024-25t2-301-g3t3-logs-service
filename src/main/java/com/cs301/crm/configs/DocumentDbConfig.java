package com.cs301.crm.configs;

import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentDbConfig {
    private MongoProperties properties;

    public static final String KEY_STORE_PATH = "/tmp/certs/rds-truststore.jks";
    public static final String TRUST_STORE_PASSWORD = "password";

    public DocumentDbConfig(final MongoProperties properties) {
        this.properties = properties;
    }

    @Bean
    public MongoClientSettingsBuilderCustomizer mongoClientSettingsBuilderCustomizer() {
        return builder -> builder.applyToSslSettings(ssl -> ssl.enabled(true));
    }

    static {
        System.setProperty("javax.net.ssl.trustStore", KEY_STORE_PATH);
        System.setProperty("javax.net.ssl.trustStorePassword", TRUST_STORE_PASSWORD);
    }
}
