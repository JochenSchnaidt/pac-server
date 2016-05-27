package com.prodyna.pac.database;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.*;

@Profile(value = { "development" })
@Configuration
@EnableMongoRepositories(basePackages = "com.prodyna.pac.persistence")
public class MongoDevelopmentConfiguration extends AbstractMongoConfiguration {

    @Value("${mongodb.host}")
    String host;

    @Value("${mongodb.port}")
    int port;

    @Value("${mongodb.database}")
    String database;

    @Value("${mongodb.authDatabase}")
    String authDatabase;

    @Value("${mongodb.user}")
    String user;

    @Value("${mongodb.password}")
    String password;

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Override
    public Mongo mongo() throws Exception {
        return getMongoClient();
    }

    @Override
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(new SimpleMongoDbFactory(getMongoClient(), getDatabaseName()));
    }

    private MongoClient getMongoClient() {

        final List<MongoCredential> credentials = new ArrayList<>();
        MongoCredential credential = MongoCredential.createScramSha1Credential(user, authDatabase, password.toCharArray());
        credentials.add(credential);

        MongoClientOptions options = MongoClientOptions.builder().cursorFinalizerEnabled(false).build();

        return new MongoClient(new ServerAddress(host, port), credentials, options);
    }

}