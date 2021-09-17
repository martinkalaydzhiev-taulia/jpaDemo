package com.example.demo;

import org.testcontainers.containers.MySQLContainer;

public class BaseTestContainers {
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest")
            .withDatabaseName("f1stats-db")
            .withUsername("user")
            .withPassword("1234");

    static {
        mySQLContainer.start();
    }
}
