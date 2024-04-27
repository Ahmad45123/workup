package com.workup.contracts.models;

import com.workup.shared.enums.contracts.MilestoneState;
import java.util.Date;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Builder(setterPrefix = "with")
@Table("contract_milestones")
public class ContractMilestone {

  @PrimaryKey
  private UUID milestoneId;

  @Indexed
  private final String contractId;

  private final String description;
  private final Date dueDate;
  private final double amount;

  @Setter
  private MilestoneState status;
}
