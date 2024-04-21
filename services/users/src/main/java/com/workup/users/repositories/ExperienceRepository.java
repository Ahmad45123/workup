package com.workup.users.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.workup.users.db.Experience;

public interface ExperienceRepository extends MongoRepository<Experience, String> {
}
