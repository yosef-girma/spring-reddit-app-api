package com.example.reddit;

import org.testcontainers.containers.PostgreSQLContainer;

public class BaseTest {

    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:13")
            .withDatabaseName("reddit-integration-test")
            .withUsername("sa")
            .withPassword("sa");
    static {
        postgreSQLContainer.start();
    }

}
