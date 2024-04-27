package com.workup.contracts.repositories;

import com.workup.contracts.models.ContractMilestone;
import java.util.List;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractMilestoneRepository
  extends CassandraRepository<ContractMilestone, UUID> {
  @Query("SELECT * FROM contracts_data.contract_milestones WHERE contractid = ?0")
  List<ContractMilestone> findByContractId(String contractId);
}
