package com.workup.users.repositories;

import com.workup.users.db.Freelancer;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FreelancerRepository extends MongoRepository<Freelancer, String> {
  // find by freelancerEmail
  Optional<Freelancer> findByEmail(String email);
}
