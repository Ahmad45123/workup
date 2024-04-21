package com.workup.contracts.models;

import com.workup.shared.enums.contracts.MilestoneState;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Getter
@Builder(setterPrefix = "with")
@Table("contract_milestones")
public class ContractMilestone {

    @PrimaryKey
    private UUID milestoneId;
    private final String contractId;

    private final String description;
    private final String dueDate;
    private final String amount;
    private final MilestoneState status;

}
