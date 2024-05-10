package com.workup.users.repositories;

import com.workup.users.db.Client;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepository extends MongoRepository<Client, String> {
  // find by clientEmail
  Optional<Client> findByEmail(String email);
}
