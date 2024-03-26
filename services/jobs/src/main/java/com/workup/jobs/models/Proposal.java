package com.workup.jobs.models;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.workup.shared.commands.jobs.proposals.JobDuration;
import com.workup.shared.commands.jobs.proposals.ProposalStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@Table("proposals")
public class Proposal {
    @PrimaryKey
    private UUID id;
    @Indexed
    private String jobId;
    private String freelancerId;
    private String coverLetter;
    private JobDuration duration;
    private ProposalStatus status;
    @CassandraType(type = CassandraType.Name.LIST, typeArguments =  CassandraType.Name.UDT, userTypeName = "milestone")
    private Milestone[] milestones; 
    @CassandraType(type = CassandraType.Name.LIST, typeArguments = CassandraType.Name.UDT, userTypeName = "attachment")
    private Attachment[] attachments;

    private Date createdAt;
    private Date updatedAt;
    
}
