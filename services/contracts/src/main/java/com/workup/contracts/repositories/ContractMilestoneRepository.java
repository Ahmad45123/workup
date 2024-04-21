package com.workup.contracts.repositories;

import com.workup.contracts.models.ContractMilestone;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractMilestoneRepository
  extends CassandraRepository<ContractMilestone, UUID> {}
