package com.workup.shared.commands.jobs.proposals;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonSerialize
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = ProposalAttachment.ProposalAttachmentBuilder.class)
public class ProposalAttachment {
    private final String name;
    private final String url;
}
