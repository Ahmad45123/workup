package com.workup.contracts.repositories;

import java.util.UUID;

import com.workup.contracts.models.ContractMilestone;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractMilestoneRepository extends CassandraRepository<ContractMilestone, UUID>{

}
