package com.workup.users.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.workup.users.db.Freelancer;

public interface FreelancerRepository extends MongoRepository<Freelancer, String> {

}
