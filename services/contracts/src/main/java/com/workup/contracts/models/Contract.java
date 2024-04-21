package com.workup.contracts.models;

import com.workup.shared.enums.contracts.ContractState;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Builder(setterPrefix = "with")
@Table("contracts")
public class Contract {

  @PrimaryKey
  private UUID contractId;

  private String jobTitle;
  private String jobId;
  private String proposalId;

  private String freelancerId;
  private String clientId;

  private List<String> milestonesIds;
  private ContractState status;
}
