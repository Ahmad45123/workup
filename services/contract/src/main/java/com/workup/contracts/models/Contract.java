package com.workup.contracts.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.enums.contracts.ContractState;
import lombok.Builder;

import java.util.Date;
import java.util.UUID;

import lombok.Getter;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Builder(setterPrefix = "with")
@Table("contracts")
public class Contract {

    @PrimaryKey
    private UUID contractId;

    private String jobId;
    private String proposalId;

    private String freelancerId;
    private String clientId;

    private String[] milestonesIds;
    private ContractState status;

}
