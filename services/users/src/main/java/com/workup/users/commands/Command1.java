package com.workup.users.commands;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;

import com.workup.users.db.Freelancer;
import com.workup.users.repositories.FreelancerRepository;

public class Command1 {
    @Autowired
    private FreelancerRepository freelancerRepository;

    public Command1(FreelancerRepository freelancerRepository) {
        this.freelancerRepository = freelancerRepository;
    }

    public void execute() {
        Query query = new Query();
        // List<Freelancer> freelancers = freelancerRepository.findAll(null)
    }
}
