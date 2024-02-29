package com.workup.jobs;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.workup.shared.commands.jobs.CreateJobRequest;
import com.workup.shared.commands.jobs.CreateProposalRequest;

@SpringBootApplication
public class JobsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobsApplication.class, args);
	}


	@Bean
    public ApplicationRunner runner(AmqpTemplate template) {
        return args -> {
			CreateJobRequest request = new CreateJobRequest("123", "Ahmed was here", "Asdsdf");
            CreateProposalRequest request2 = new CreateProposalRequest("123", "Shimaa was here");
                
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

