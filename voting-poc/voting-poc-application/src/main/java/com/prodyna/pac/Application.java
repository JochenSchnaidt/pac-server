package com.prodyna.pac;

import static com.prodyna.pac.Constants.KNOWN_STAGES;
import static com.prodyna.pac.Constants.SYSTEM_VARIABLE;

import java.util.Map;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication(Application.class);

        Optional<String> stage = determineStage();

        if (stage.isPresent()) {
            application.setAdditionalProfiles(stage.get());
            application.run(args);
        } else {
            System.err.println("No valid stage configuration detected. Application shut down.");
        }
    }

    private static Optional<String> determineStage() {

        Map<String, String> systemEnv = System.getenv();

        if (systemEnv.containsKey(SYSTEM_VARIABLE) || systemEnv.containsKey(SYSTEM_VARIABLE.toUpperCase())) {

            String stage = systemEnv.get(SYSTEM_VARIABLE).toLowerCase();

            if (KNOWN_STAGES.contains(stage)) {
                return Optional.of(stage);
            }

            return Optional.empty();

        } else {
            return Optional.empty();
        }
    }

}