package com.workup.jobs.repositories;

import com.workup.jobs.models.Proposal;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

public interface ProposalRepository
    extends CassandraRepository<Proposal, Proposal.ProposalPrimaryKey> {
  @Query("SELECT * FROM jobs_data.proposals WHERE job_id = ?0")
  @Cacheable(value = "proposals", key = "#jobId")
  public List<Proposal> findByJobId(String jobId);

  @Query("SELECT * FROM jobs_data.proposals WHERE freelancer_id = ?0")
  public List<Proposal> findByFreelancerId(String freelancerId);

  @CacheEvict(value = "proposals", key = "#entity.primaryKey.jobId")
  <S extends Proposal> S save(S entity);
}
