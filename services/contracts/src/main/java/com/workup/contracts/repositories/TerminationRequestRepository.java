package com.workup.contracts.repositories;

import com.workup.contracts.models.TerminationRequest;
import com.workup.shared.enums.contracts.TerminationRequestStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminationRequestRepository
    extends CassandraRepository<TerminationRequest, UUID> {
  @AllowFiltering
  List<TerminationRequest> findByRequesterIdAndContractIdAndStatus(
      String requesterId, String contractId, TerminationRequestStatus status);
}
