package com.workup.jobs.commands;

import java.util.UUID;

import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Stream;

import com.workup.jobs.models.Attachment;
import com.workup.jobs.models.Milestone;
import com.workup.jobs.models.Proposal;
import com.workup.shared.commands.jobs.proposals.requests.CreateProposalRequest;

public class CreateProposalCommand extends JobCommand<CreateProposalRequest> {

    @Override
    public void Run(CreateProposalRequest request) {
        // map request atttachment to proposal attachment
       request.getAttachments().stream().map(attachment -> Attachment.builder()
                .withName(attachment.getName())
                .withUrl(attachment.getUrl())
                .build()).toArray(Attachment[]::new);


        
        Proposal proposal = Proposal.builder()
                .withId(UUID.randomUUID())
                .withJobId(request.getJobId())
                .withFreelancerId(request.getFreelancerId())
                .withCoverLetter(request.getCoverLetter())
                .withDuration(request.getJobDuration())
                .withAttachments(request.getAttachments().stream().map(attachment -> Attachment.builder()
                .withName(attachment.getName())
                .withUrl(attachment.getUrl())
                .build()).toArray(Attachment[]::new))
                .withMilestones(request.getMilestones().stream().map(milestone -> Milestone.builder().withAmount(milestone.getAmount())
                .withDescription(milestone.getDescription())
                .withDueDate(milestone.getDueDate())
                .build()).toArray(Milestone[]::new))
                .build();
        try{
        Proposal savedProposal = proposalRepository.save(proposal);
        System.out.println(" [x] Saved Proposal '" + savedProposal.getCoverLetter()) ;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
