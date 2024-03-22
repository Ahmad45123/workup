package com.workup.shared.commands.jobs.proposals;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;

public class ProposalModel {

    public String id;
    public String jobId;
    public String freelancerId;
    public String coverLetter;
    public ProposalStatus status;
    public Date createdAt;
    public Date modifiedAt;
    public JobDuration duration;
    public ArrayList<ProposalAttachment> attachments;
    public ArrayList<ProposalMilestone> milestones;

    @JsonCreator
    public ProposalModel(
            @JsonProperty("id") String id,
            @JsonProperty("jobId") String jobId,
            @JsonProperty("freelancerId") String freelancerId,
            @JsonProperty("coverLetter") String coverLetter,
            @JsonProperty("status") ProposalStatus status,
            @JsonProperty("createdAt") Date createdAt,
            @JsonProperty("modifiedAt") Date modifiedAt,
            @JsonProperty("duration") JobDuration jobDuration,
            @JsonProperty("attachments") ArrayList<ProposalAttachment> attachments,
            @JsonProperty("milestones") ArrayList<ProposalMilestone> milestones) {
        this.id = id;
        this.jobId = jobId;
        this.freelancerId = freelancerId;
        this.coverLetter = coverLetter;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.duration = jobDuration;
        this.attachments = attachments;
        this.milestones = milestones;
    }
}
