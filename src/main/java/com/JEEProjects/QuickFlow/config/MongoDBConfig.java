package com.JEEProjects.QuickFlow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.connection.ConnectionPoolSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import java.util.concurrent.TimeUnit; // Add this import

@Configuration
public class MongoDBConfig {
    @Value("${spring.data.mongodb.uri}")
    private String mongoURI;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${spring.data.mongodb.max-idle-time}")
    private long maxIdleTime;

 

    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(mongoURI);
        MongoClientSettings clientSettings = MongoClientSettings.builder()
            .retryWrites(true)
            .applyConnectionString(connectionString)
            .applyToConnectionPoolSettings((ConnectionPoolSettings.Builder builder) -> {
                builder.maxSize(100)
                       .minSize(5)
                       .maxConnectionLifeTime(30, TimeUnit.MINUTES)
                       .maxConnectionIdleTime(maxIdleTime, TimeUnit.MILLISECONDS);
            })
            .applyToSocketSettings(builder -> {
                builder.connectTimeout(50000, TimeUnit.MILLISECONDS);
            })

            .build();

        return MongoClients.create(clientSettings);
    }
}
