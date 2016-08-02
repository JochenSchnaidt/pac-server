package com.prodyna.pac.database;

import static com.prodyna.pac.Constants.STAGE_PRODUCTION;
import static com.prodyna.pac.Constants.STAGE_QUALITY_ASSURANCE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

@Profile(value = { STAGE_QUALITY_ASSURANCE, STAGE_PRODUCTION })
@Configuration
@EnableMongoRepositories(basePackages = "com.prodyna.pac.persistence")
public class MongoProductionConfiguration extends AbstractMongoConfiguration {

    @Value("${mongodb.server1.host}")
    String host1;
    @Value("${mongodb.server1.port}")
    int port1;

    @Value("${mongodb.server2.host}")
    String host2;
    @Value("${mongodb.server2.port}")
    int port2;

    @Value("${mongodb.server3.host}")
    String host3;
    @Value("${mongodb.server3.port}")
    int port3;

    @Value("${mongodb.database}")
    String database;

    @Override
    protected String getDatabaseName() {
        return database;
    }

    private Map<String, Object> servers;

    public Map<String, Object> getServers() {
        return servers;
    }

    public void setServers(Map<String, Object> servers) {
        this.servers = servers;
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

        List<ServerAddress> seeds = new ArrayList<ServerAddress>();
        seeds.add(new ServerAddress(host1, port1));
        seeds.add(new ServerAddress(host2, port2));
        seeds.add(new ServerAddress(host3, port3));

        MongoClient client = new MongoClient(seeds);
        return client;
    }

}