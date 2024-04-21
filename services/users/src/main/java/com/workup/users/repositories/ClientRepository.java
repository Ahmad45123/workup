package com.workup.users.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.workup.users.db.Client;

public interface ClientRepository extends MongoRepository<Client, String> {
}
