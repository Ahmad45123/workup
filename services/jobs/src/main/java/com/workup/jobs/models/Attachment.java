package com.workup.jobs.models;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@UserDefinedType
public class Attachment {
    private String url;
    private String name;  
}
