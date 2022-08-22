package com.example.demowebsocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"com.example.demowebsocket.mongo.repository"})
public class MongoConfig {
}
