package com.ikes.sales;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class SalesApplication {
  @Resource
  private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(SalesApplication.class, args);
	}

	@PostConstruct
  public void runLiquibase() throws Exception {
    String dataSourceUrl = environment.getProperty("spring.datasource.url");
    String dataSourceUsername = environment.getProperty("spring.datasource.username");
    String dataSourcePassword = environment.getProperty("spring.datasource.password");

    Properties props = new Properties();
    props.setProperty("user", dataSourceUsername);
    props.setProperty("password", dataSourcePassword);
    Connection connection = DriverManager.getConnection(dataSourceUrl, props);

    Database database = DatabaseFactory.getInstance()
      .findCorrectDatabaseImplementation(new JdbcConnection(connection));
    Liquibase liquibase = new liquibase.Liquibase(
      "db/changelog/db.changelog-master.yaml",
      new ClassLoaderResourceAccessor(),
      database);
    liquibase.update(new Contexts(), new LabelExpression());
  }
}
