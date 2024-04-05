package com.workup.jobs.repositories;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import com.workup.jobs.models.Proposal;


public interface ProposalRepository extends CassandraRepository<Proposal,Proposal.ProposalPrimaryKey>{

    @Query("SELECT * FROM jobs_data.proposals WHERE job_id = ?0")
    public List<Proposal> findByJobId(String jobId);

    @Query("SELECT * FROM jobs_data.proposals WHERE freelancer_id = ?0")
    public List<Proposal> findByFreelancerId(String freelancerId);
    
}
