package com.workup.users.db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.workup.users.repositories.ClientRepository;

// @EnableMongoRepositories(basePackageClasses = ClientRepository.class)
@Configuration
public class MongoDBConfig {

}