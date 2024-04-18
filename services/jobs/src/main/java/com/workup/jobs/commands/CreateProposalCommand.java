package com.workup.jobs.commands;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Stream;

import com.workup.jobs.models.Attachment;
import com.workup.jobs.models.Milestone;
import com.workup.jobs.models.Proposal;
import com.workup.shared.commands.jobs.proposals.requests.CreateProposalRequest;
import com.workup.shared.commands.jobs.proposals.responses.CreateProposalResponse;

public class CreateProposalCommand extends JobCommand<CreateProposalRequest, CreateProposalResponse> {

    @Override
    public CreateProposalResponse Run(CreateProposalRequest request) {     
        Proposal proposal = Proposal.builder()
                .withPrimaryKey(Proposal.ProposalPrimaryKey.builder().withJobId(request.getJobId())
                .withId(UUID.randomUUID()).build())
                .withFreelancerId(request.getFreelancerId())
                .withCoverLetter(request.getCoverLetter())
                .withDuration(request.getJobDuration())
                .withAttachments(request.getAttachments().stream().map(attachment -> Attachment.builder()
                .withName(attachment.getName())
                .withUrl(attachment.getUrl())
                .build()).collect(Collectors.toCollection(ArrayList::new)))
                .withMilestones(request.getMilestones().stream().map(milestone -> Milestone.builder().withAmount(milestone.getAmount())
                .withDescription(milestone.getDescription())
                .withDueDate(milestone.getDueDate())
                .build()).collect(Collectors.toCollection(ArrayList::new)))
                .build();
        try{
            Proposal savedProposal = proposalRepository.save(proposal);
            System.out.println(" [x] Saved Proposal '" + savedProposal.getCoverLetter()) ;
            return CreateProposalResponse.builder()
                    .withSuccess(true)
                    .withId(savedProposal.getPrimaryKey().getId().toString())
                    .build();
        }
        catch(Exception e){
            e.printStackTrace();
            return CreateProposalResponse.builder()
                    .withSuccess(false)
                    .withId(null)
                    .build();
        }
    }

    
}
