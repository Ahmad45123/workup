package com.workup.users.repositories;

import com.workup.users.db.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepository extends MongoRepository<Client, String> {}
