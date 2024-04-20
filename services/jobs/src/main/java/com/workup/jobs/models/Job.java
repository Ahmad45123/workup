package com.workup.jobs.models;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.SASI;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.SASI.IndexMode;

import com.workup.shared.enums.jobs.Experience;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@Table("jobs")
public class Job {

    @PrimaryKey
    private UUID id;
    
    @SASI(indexMode = IndexMode.CONTAINS)
    private String title;
    
    @SASI(indexMode = IndexMode.CONTAINS)
    private String description;
    
    private String location;
    private double budget;

    @CassandraType(type = CassandraType.Name.LIST, typeArguments = CassandraType.Name.TEXT)
    private String[] skills;
    private Experience experienceLevel;
    private boolean isActive;
    @Indexed
    @Column("client_id")
    private String clientId;
    private Date createdAt;
    private Date updatedAt;
    
}

