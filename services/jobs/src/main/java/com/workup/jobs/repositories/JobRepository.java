package com.workup.jobs.repositories;

import com.workup.jobs.models.Job;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface JobRepository extends CassandraRepository<Job, UUID> {
  @Query("SELECT * FROM jobs_data.jobs WHERE search_index LIKE ?0")
  public Slice<Job> searchForJob(String searchTerm, Pageable pg);

  @Query("SELECT * FROM jobs_data.jobs WHERE client_id = ?0")
  public List<Job> getJobsByClientId(String clientId);

  @Cacheable(value = "jobs", key = "#jobId")
  public Optional<Job> findById(UUID jobId);

  @CacheEvict(value = "jobs", key = "#entity.id")
  <S extends Job> S save(S entity);
}
