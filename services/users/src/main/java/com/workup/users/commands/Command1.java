package com.workup.users.commands;

import com.workup.users.repositories.FreelancerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;

public class Command1 {
  @Autowired private FreelancerRepository freelancerRepository;

  public Command1(FreelancerRepository freelancerRepository) {
    this.freelancerRepository = freelancerRepository;
  }

  public void execute() {
    Query query = new Query();
    // List<Freelancer> freelancers = freelancerRepository.findAll(null)
  }
}
