package com.workup.users.repositories;

import com.workup.users.db.Client;
import java.util.Optional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepository extends MongoRepository<Client, String> {
  // find by clientEmail
  @Cacheable(value = "clients", key = "#email")
  Optional<Client> findByEmail(String email);

  @CacheEvict(value = "clients", key = "#entity.email")
  <S extends Client> S save(S entity);
}
