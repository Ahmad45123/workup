package com.workup.jobs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workup.shared.commands.jobs.proposals.requests.CreateProposalRequest;
import com.workup.jobs.commands.SearchJobsCommand;
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

import com.workup.shared.commands.jobs.requests.CreateJobRequest;
import com.workup.shared.commands.jobs.requests.SearchJobsRequest;
import com.workup.shared.commands.jobs.responses.CreateJobResponse;
import com.workup.shared.commands.jobs.responses.SearchJobsResponse;
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
                    .withTitle("Convert HTML Template to React 3")
                    .withDescription("I have an HTML template that I have purchased and own the rights to. I would like it converted into a React application.")
                    .withSkills(new String[] {"HTML",
                        "CSS",
                        "JavaScript",
                        "React"})
                    .build();
            template.convertSendAndReceive("jobsqueue", createJobRequest);

            createJobRequest = CreateJobRequest.builder()
                    .withTitle("Create an interactive web platform where users can create profiles, view them as nodes in a graph")
                    .withDescription("Create an interactive web platform where users can create couch profiles (how do you describe yourself as a couch), view them as nodes in a graph, and interact with others.")
                    .withSkills(new String[] {
                        "Nodejs",
                        "Neo4j",
                        "Javascript"})
                    .build();
            template.convertSendAndReceive("jobsqueue", createJobRequest);
            
            SearchJobsRequest searchJobsCommand = SearchJobsRequest.builder().withPageLimit(5).withQuery("HTML").build();
            SearchJobsResponse resp = (SearchJobsResponse)template.convertSendAndReceive("jobsqueue", searchJobsCommand);
            
            System.out.println("Search results: " + resp.getJobs().length);
            for (int i = 0; i < resp.getJobs().length; i++) {
                System.out.println("Job: " + resp.getJobs()[i].getTitle());
            }
            System.out.println(resp.getPagingState());

            System.out.println("*****");

            searchJobsCommand = SearchJobsRequest.builder().withPagingState(resp.getPagingState()).withPageLimit(5).withQuery("HTML").build();
            resp = (SearchJobsResponse)template.convertSendAndReceive("jobsqueue", searchJobsCommand);
            
            System.out.println("Search results 2: " + resp.getJobs().length);
            for (int i = 0; i < resp.getJobs().length; i++) {
                System.out.println("Job: " + resp.getJobs()[i].getTitle());
            }
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

