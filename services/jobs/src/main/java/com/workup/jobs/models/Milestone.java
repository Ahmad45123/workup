package com.workup.jobs.models;

import java.util.Date;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder(setterPrefix = "with")
@UserDefinedType
public class Milestone {
    private String description;
    private Date dueDate;
    private double amount;
}
