package com.workup.jobs;

import com.workup.shared.commands.jobs.proposals.CreateProposalRequest;
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

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;

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
            milestones.add(new ProposalMilestone("Milestone 1", 100, new Timestamp(System.currentTimeMillis())));
            ArrayList<ProposalAttachment> attachments = new ArrayList<>();
            attachments.add(new ProposalAttachment("placeholder", "https://picsum.photos/200/300"));
            CreateProposalRequest request2 = new CreateProposalRequest("123", "Shimaa was here", JobDuration.LESS_THAN_A_MONTH, attachments, milestones);

            template.convertAndSend("jobsqueue", request);
            template.convertAndSend("jobsqueue", request2);
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

