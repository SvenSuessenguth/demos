package org.cc.testing.dbunit.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DefaultMetadataHandler;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Testcontainers
public abstract class AbstractDbUnitJpaIT {


  private EntityManagerFactory entityManagerFactory;
  protected EntityManager entityManager;

  // https://stackoverflow.com/questions/69132686/how-can-i-set-the-port-for-postgresql-when-using-testcontainers
  @SuppressWarnings("all")
  @Container
  public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.2")
    .withDatabaseName("testing")
    .withUsername("postgres")
    .withPassword("postgres");

  @BeforeEach
  public void initTestFixture() {
    // overriding defaults from persistence.xml
    // see https://www.generacodice.com/en/articolo/1290009/Read-Environment-Variables-in-persistence.xml-file
    Map<String, Object> configOverrides = new HashMap<>();
    configOverrides.put("jakarta.persistence.jdbc.url", postgreSQLContainer.getJdbcUrl());

    // Get the entity manager for the tests.
    entityManagerFactory = Persistence.createEntityManagerFactory("testcontainersPU", configOverrides);
    entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
  }

  @AfterEach
  public void closeTestFixture() {
    entityManager.getTransaction().rollback();
    entityManager.close();
    entityManagerFactory.close();
  }

  public void initDatabase(String testData) throws IOException, DatabaseUnitException, SQLException {
    // Connection aufbauen
    Connection connection = entityManager.unwrap(Connection.class);
    IDatabaseConnection dbunitConn = new DatabaseConnection(connection);
    DatabaseConfig dbConfig = dbunitConn.getConfig();
    dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
    dbConfig.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new DefaultMetadataHandler());
    dbConfig.setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, Boolean.TRUE);

    // Testdaten laden und [NULL] durch null-Value ersetzen
    URL url = getClass().getClassLoader().getResource(testData);
    InputStream is = Objects.requireNonNull(url).openStream();
    ReplacementDataSet dataSet = new ReplacementDataSet(new FlatXmlDataSetBuilder().build(is));
    dataSet.addReplacementObject("[NULL]", null);

    // Daten in Datenbank eintragen
    DatabaseOperation.DELETE_ALL.execute(dbunitConn, dataSet);
    DatabaseOperation.CLEAN_INSERT.execute(dbunitConn, dataSet);
  }
}