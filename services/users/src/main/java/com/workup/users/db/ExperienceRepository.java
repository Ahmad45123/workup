package com.workup.users.db;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExperienceRepository extends MongoRepository<Experience,String> {
}
