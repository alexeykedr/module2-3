package org.example.util;

import org.flywaydb.core.Flyway;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MyFlywayMigration {

    public static void migrate() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(
                "ssrc/main/resources/flyway.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Flyway flyway = Flyway.configure()
                .dataSource(properties.getProperty("flyway.url"),
                        properties.getProperty("flyway.user"),
                        properties.getProperty("flyway.password"))
                .locations(properties.getProperty("flyway.locations"))
                .load();

        if (!flyway.validateWithResult().validationSuccessful) {
            flyway.migrate();
        }
    }
}
