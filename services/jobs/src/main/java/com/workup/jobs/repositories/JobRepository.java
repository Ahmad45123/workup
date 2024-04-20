package com.workup.jobs.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.workup.jobs.models.Job;

public interface JobRepository extends CassandraRepository<Job, UUID>{
    
    @Query("SELECT * FROM jobs_data.jobs WHERE title LIKE ?0")
    public Slice<Job> searchForJob(String searchTerm, Pageable pg);

    @Query("SELECT * FROM jobs_data.jobs WHERE client_id = ?0")
    public List<Job> getJobsByClientId(String clientId);
}
