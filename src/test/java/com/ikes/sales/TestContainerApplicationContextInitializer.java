package com.ikes.sales;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class TestContainerApplicationContextInitializer implements
  ApplicationContextInitializer<ConfigurableApplicationContext> {
  public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
    PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1");
    postgreSQLContainer.start();
    TestPropertyValues.of(
      "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
      "spring.datasource.username=" + postgreSQLContainer.getUsername(),
      "spring.datasource.password=" + postgreSQLContainer.getPassword(),
      "spring.r2dbc.url=" + postgreSQLContainer.getJdbcUrl()
        .replaceAll("jdbc", "r2dbc"),
      "spring.r2dbc.username=" + postgreSQLContainer.getUsername(),
      "spring.r2dbc.password=" + postgreSQLContainer.getPassword()
    ).applyTo(configurableApplicationContext.getEnvironment());
  }
}
