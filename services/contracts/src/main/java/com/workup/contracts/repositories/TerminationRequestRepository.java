package com.workup.contracts.repositories;

import com.workup.contracts.models.TerminationRequest;
import com.workup.shared.enums.contracts.TerminationRequestStatus;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TerminationRequestRepository extends CassandraRepository<TerminationRequest, UUID> {

    List<TerminationRequest> findByRequesterIdAndContractIdAndStatus(String requesterId, String contractId, TerminationRequestStatus status);

}
