package com.workup.users.repositories;

import com.workup.users.db.Achievement;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AchievementRepository extends MongoRepository<Achievement, String> {}
