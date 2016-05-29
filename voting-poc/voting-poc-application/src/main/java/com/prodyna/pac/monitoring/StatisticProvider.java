package com.prodyna.pac.monitoring;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.export.MBeanExportException;
import org.springframework.jmx.export.MBeanExporter;

@EnableMBeanExport
@Configuration
public class StatisticProvider {

    private final static String VOTING_POC = "VotingPoC:";
    private final static String FOLDER = "=";
    private final static String NAME = ",name=";

    private final Map<String, StatisticData> beans = new ConcurrentHashMap<>();
    private final Map<String, String> knownFolders = new ConcurrentHashMap<>();

    private MBeanExporter exporter;

    @Bean
    public MBeanExporter mbeanExporter() {
        exporter = new MBeanExporter();
        return exporter;
    }

    public void updateValue(String identifier, String folder, String method, long duration) throws MBeanExportException, MalformedObjectNameException, NullPointerException {

        if (beans.containsKey(identifier)) {
            StatisticData stat = beans.get(identifier);
            stat.updateValues(duration);
        } else {
            StatisticData stat = new StatisticData(identifier);
            stat.updateValues(duration);

            String mBeanIdentifier;

            if (knownFolders.keySet().contains(folder)) {
                mBeanIdentifier = knownFolders.get(folder) + method;
            } else {
                String temp = VOTING_POC + beans.size() + FOLDER + folder + NAME;
                knownFolders.put(folder, temp);
                mBeanIdentifier = temp + method;
            }

            beans.put(identifier, stat);

            exporter.registerManagedResource(stat, ObjectName.getInstance(mBeanIdentifier));
        }

        beans.values().forEach(stat -> System.out.println(stat.toString()));
    }

}
