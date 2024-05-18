package com.workup.users.repositories;

import com.workup.users.db.Freelancer;
import java.util.Optional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FreelancerRepository extends MongoRepository<Freelancer, String> {
  // find by freelancerEmail
  @Cacheable(value = "freelancers", key = "#email")
  Optional<Freelancer> findByEmail(String email);

  @CacheEvict(value = "freelancers", key = "#entity.email")
  <S extends Freelancer> S save(S entity);
}
