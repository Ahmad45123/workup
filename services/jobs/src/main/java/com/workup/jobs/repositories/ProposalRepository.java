package com.workup.jobs.repositories;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.workup.jobs.models.Proposal;

public interface ProposalRepository extends CassandraRepository<Proposal, UUID>{
    
}
