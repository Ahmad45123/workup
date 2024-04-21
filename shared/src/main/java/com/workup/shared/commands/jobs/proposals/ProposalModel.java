package com.workup.shared.commands.jobs.proposals;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.ArrayList;
import java.util.Date;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@JsonSerialize
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class ProposalModel {

  private final String id;
  private final String jobId;
  private final String freelancerId;
  private final String coverLetter;
  private final ProposalStatus status;
  private final Date createdAt;
  private final Date modifiedAt;
  private final JobDuration duration;
  private final ArrayList<ProposalAttachment> attachments;
  private final ArrayList<ProposalMilestone> milestones;
}
