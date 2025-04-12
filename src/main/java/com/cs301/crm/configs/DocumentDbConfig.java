package com.cs301.crm.configs;

import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentDbConfig {
    @Bean
    public MongoClientSettingsBuilderCustomizer mongoClientSettingsBuilderCustomizer() {
        return builder -> builder.applyToSslSettings(ssl -> ssl.enabled(true));
    }
}
