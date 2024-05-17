package com.workup.users.repositories;

import com.workup.users.db.Client;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepository extends MongoRepository<Client, String> {
  // find by clientEmail
  @Cacheable(value = "users_findByEmail", key = "#email")
  Optional<Client> findByEmail(String email);
}
