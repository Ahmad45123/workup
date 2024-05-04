package com.workup.users.repositories;

import com.workup.users.db.Education;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EducationRepository extends MongoRepository<Education, String> {}
