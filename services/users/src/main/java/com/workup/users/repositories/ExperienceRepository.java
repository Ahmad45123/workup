package com.workup.users.repositories;

import com.workup.users.db.Experience;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExperienceRepository extends MongoRepository<Experience, String> {}
