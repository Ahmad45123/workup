package com.workup.shared.commands.jobs.proposals;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.workup.shared.commands.CommandRequest;

import java.util.ArrayList;

public class CreateProposalRequest extends CommandRequest {
    public String freelancerId;
    public String coverLetter;
    public String jobId;
    public JobDuration jobDuration;

    public ArrayList<ProposalAttachment> attachments;
    public ArrayList<ProposalMilestone> milestones;


    @JsonCreator
    public CreateProposalRequest(@JsonProperty("freelancerId") String freelancerId,
                                 @JsonProperty("jobId") String jobId,
                                 @JsonProperty("coverLetter") String coverLetter,
                                 @JsonProperty("duration") JobDuration jobDuration,
                                 @JsonProperty("attachments") ArrayList<ProposalAttachment> attachments,
                                 @JsonProperty("milestones") ArrayList<ProposalMilestone> milestones) {
        this.freelancerId = freelancerId;
        this.jobId = jobId;
        this.coverLetter = coverLetter;
        this.jobDuration = jobDuration;
        this.attachments = attachments;
        this.milestones = milestones;
    }
}
