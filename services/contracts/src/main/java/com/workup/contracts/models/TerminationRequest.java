package com.workup.contracts.models;

import com.workup.shared.enums.contracts.TerminationRequestStatus;
import java.util.Date;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Builder(setterPrefix = "with")
@Table("termination_requests")
public class TerminationRequest {

  @PrimaryKey
  private UUID requestId;

  private String contractId;
  private String requesterId;
  private String reason;

  @Setter
  private TerminationRequestStatus status;
}
