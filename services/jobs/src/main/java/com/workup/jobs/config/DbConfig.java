package com.workup.jobs.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "com.workup.jobs.models")
public class DbConfig extends AbstractCassandraConfiguration {

  @Value("${spring.cassandra.keyspace-name}")
  private String keyspaceName;

  @Value("${spring.cassandra.local-datacenter}")
  private String localDataCenter;

  @Value("${spring.cassandra.contact-points}")
  private String contactPoints;

  @Value("${spring.cassandra.schema-action}")
  private SchemaAction schemaAction;

  @Override
  protected String getKeyspaceName() {
    return keyspaceName;
  }

  @Override
  protected String getContactPoints() {
    return contactPoints;
  }

  @Override
  protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
    CreateKeyspaceSpecification specification =
        CreateKeyspaceSpecification.createKeyspace(keyspaceName)
            .ifNotExists()
            .with(KeyspaceOption.DURABLE_WRITES, true)
            .withSimpleReplication();
    return Arrays.asList(specification);
  }

  @Override
  public String[] getEntityBasePackages() {
    return new String[] {"com.workup.jobs.models"};
  }

  @Override
  protected String getLocalDataCenter() {
    return "datacenter1";
  }

  @Override
  public SchemaAction getSchemaAction() {
    return schemaAction; // Enable automatic schema creation
  }
}
