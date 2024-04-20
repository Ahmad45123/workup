package com.workup.contracts.models;

import com.workup.shared.enums.contracts.TerminationRequestStatus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.UUID;

@Getter
@Builder(setterPrefix = "with")
@Table("termination_requests")
public class TerminationRequest {

    @PrimaryKey
    private UUID requestId;
    private String contractId;
    private String requesterId;
    private String reason;
    private TerminationRequestStatus status;
    private Date date;

}
