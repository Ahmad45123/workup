package com.workup.shared.commands.jobs.proposals.responses;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.jobs.proposals.ProposalModel;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;

@Getter
@JsonSerialize
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class GetMyProposalsResponse extends CommandResponse {
    private final ArrayList<ProposalModel> proposals;
}
