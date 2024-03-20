package com.workup.jobs.models;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@Table("jobs")
public class Job {

    @PrimaryKey
    private UUID id;
    
    private String title;
    private String description;
    private String location;
    private double budget;

    @CassandraType(type = CassandraType.Name.LIST, typeArguments = CassandraType.Name.TEXT)
    private String[] skills;
    private ExperienceLevel experienceLevel;
    private boolean isActive;
    private String clientId;
    private Date createdAt;
    private Date updatedAt;
    
}

