package com.workup.jobs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workup.shared.commands.jobs.proposals.requests.CreateProposalRequest;
import com.workup.shared.commands.jobs.proposals.JobDuration;
import com.workup.shared.commands.jobs.proposals.ProposalAttachment;
import com.workup.shared.commands.jobs.proposals.ProposalMilestone;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.workup.shared.commands.jobs.CreateJobRequest;

import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
public class JobsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobsApplication.class, args);
    }


    @Bean
    public ApplicationRunner runner(AmqpTemplate template) {
        return args -> {
            CreateJobRequest request = new CreateJobRequest("123", "Ahmed was here", "Asdsdf");
            ArrayList<ProposalMilestone> milestones = new ArrayList<>();
            milestones.add(ProposalMilestone
                    .builder()
                    .withAmount(100)
                    .withDescription("Milestone 1")
                    .withDueDate(new Date(System.currentTimeMillis())).build());
            ArrayList<ProposalAttachment> attachments = new ArrayList<>();
            attachments.add(ProposalAttachment
                    .builder()
                    .withUrl("https://picsum.photos/200/300")
                    .withName("placeholder").build());
            CreateProposalRequest request2 = CreateProposalRequest
                    .builder()
                    .withFreelancerId("123")
                    .withCoverLetter("Shimaa was here")
                    .withJobId("456")
                    .withJobDuration(JobDuration.LESS_THAN_A_MONTH)
                    .withAttachments(attachments)
                    .withMilestones(milestones)
                    .build();
            String proposalJson = "{\n" +
                    "  \"freelancerId\": \"123\",\n" +
                    "  \"coverLetter\": \"Shimaa was here from json\",\n" +
                    "  \"jobId\": \"456\",\n" +
                    "  \"jobDuration\": \"LESS_THAN_A_MONTH\",\n" +
                    "  \"attachments\": [\n" +
                    "    {\n" +
                    "      \"name\": \"placeholder\",\n" +
                    "      \"url\": \"https://picsum.photos/200/300\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"milestones\": [\n" +
                    "    {\n" +
                    "      \"amount\": 100,\n" +
                    "      \"description\": \"Milestone 1\",\n" +
                    "      \"dueDate\": 1619827200000\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";
            ObjectMapper mapper = new ObjectMapper();
            CreateProposalRequest requestFromJson = mapper.readValue(proposalJson, CreateProposalRequest.class);
            template.convertAndSend("jobsqueue", request);
            template.convertAndSend("jobsqueue", requestFromJson);
        };
    }

    @Bean
    public Queue myQueue() {
        return new Queue("jobsqueue");
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

