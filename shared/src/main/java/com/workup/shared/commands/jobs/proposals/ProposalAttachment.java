package com.workup.shared.commands.jobs.proposals;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = ProposalAttachment.ProposalAttachmentBuilder.class)
public class ProposalAttachment {
    private final String name;
    private final String url;
}
