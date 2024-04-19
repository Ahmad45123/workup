package com.workup.jobs.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import com.workup.jobs.models.Job;

public interface JobRepository extends CassandraRepository<Job, UUID>{
    
    @Query("SELECT * FROM jobs_data.jobs WHERE title LIKE ?0")
    public List<Job> searchForJob(String searchTerm);
}
