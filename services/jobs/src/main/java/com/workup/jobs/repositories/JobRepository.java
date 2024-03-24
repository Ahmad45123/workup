package com.workup.jobs.repositories;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.CassandraRepository;

import com.workup.jobs.models.Job;

public interface JobRepository extends CassandraRepository<Job, UUID>{
    
}
