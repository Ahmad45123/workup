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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;


import java.util.ArrayList;
import java.util.Date;

import com.workup.shared.commands.jobs.requests.CreateJobRequest;
import com.workup.shared.commands.jobs.responses.CreateJobResponse;
import com.workup.shared.enums.jobs.Experience;

@SpringBootApplication
public class JobsApplication {


    public static void main(String[] args) {
        SpringApplication.run(JobsApplication.class, args);
    }


    @Bean
    public ApplicationRunner runner(AmqpTemplate template) {
        return args -> {
            CreateJobRequest createJobRequest = CreateJobRequest.builder()
                    .withBudget(0)
                    .withClientId("123")
                    .withTitle("title")
                    .withExperience(Experience.INTERMEDIATE)
                    .build();
            CreateJobResponse resp = (CreateJobResponse)template.convertSendAndReceive("jobsqueue", createJobRequest);
            
            System.out.println(resp.getJobId());
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

