package com.workup.contracts.config;

import java.util.List;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "com.workup.contracts.models")
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
  protected @NonNull String getKeyspaceName() {
    return keyspaceName;
  }

  @Override
  protected @NonNull String getContactPoints() {
    return contactPoints;
  }

  @Override
  protected @NonNull List<CreateKeyspaceSpecification> getKeyspaceCreations() {
    CreateKeyspaceSpecification specification =
        CreateKeyspaceSpecification.createKeyspace(keyspaceName)
            .ifNotExists()
            .with(KeyspaceOption.DURABLE_WRITES, true)
            .withSimpleReplication();
    return List.of(specification);
  }

  @Override
  public String @NonNull [] getEntityBasePackages() {
    return new String[] {"com.workup.contracts.models"};
  }

  @Override
  protected String getLocalDataCenter() {
    return localDataCenter;
  }

  @Override
  public @NonNull SchemaAction getSchemaAction() {
    return schemaAction; // Enable automatic schema creation
  }
}
