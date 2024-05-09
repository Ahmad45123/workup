package com.workup.users;

import com.workup.shared.views.users.AchievementView;
import com.workup.shared.views.users.EducationView;
import com.workup.shared.views.users.ExperienceView;
import com.workup.users.db.Achievement;
import com.workup.users.db.Education;
import com.workup.users.db.Experience;
import com.workup.users.db.Freelancer;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;

public class UsersTestUtils {
  public static Freelancer createTestFreelancer() {
    return Freelancer.builder()
        .withEmail("ahmad45123@gmail.com")
        .withPassword_hash("verysecurepassword")
        .withFull_name("Mr. Mamdouh")
        .withJob_title("Software Engineer")
        .withCity("Cairo")
        .withBirthdate(Date.from(Instant.now()))
        .withAchievements(new ArrayList<>())
        .withExperiences(new ArrayList<>())
        .withEducations(new ArrayList<>())
        .withSkills(new ArrayList<>())
        .withLanguages(new ArrayList<>())
        .build();
  }

  public static Achievement createTestAchievement() {
    return Achievement.builder()
        .withAchievement_description("Description")
        .withAchievement_name("Achievement")
        .withAward_date(Date.from(Instant.now()))
        .withAwarded_by("Award giver")
        .build();
  }

  public static Education createTestEducation() {
    return Education.builder()
        .withSchool_name("Test School")
        .withDegree("Test Degree")
        .withEducation_start_date(Date.from(Instant.now()))
        .withCity("Test City")
        .withEnd_date(Date.from(Instant.now()))
        .withMajor("Test Major")
        .withEducation_description("Test Description")
        .withGrade("Test Grade")
        .build();
  }

  public static Experience createTestExperience() {
    return Experience.builder()
        .withCompany_name("Test Company")
        .withJob_title("Test Job Title")
        .withEmployment_start(Date.from(Instant.now()))
        .withEmployment_end(Date.from(Instant.now()))
        .withExperience_description("Test Experience Description")
        .withCity("Test City")
        .build();
  }

  public static void assertAchievementEquals(Achievement expected, Achievement actual) {
    Assertions.assertEquals(expected.getAchievement_name(), actual.getAchievement_name());
    Assertions.assertEquals(
        expected.getAchievement_description(), actual.getAchievement_description());
    Assertions.assertEquals(expected.getAwarded_by(), actual.getAwarded_by());
    Assertions.assertEquals(expected.getAward_date(), actual.getAward_date());
  }

  public static void assertAchievementEquals(Achievement expected, AchievementView actual) {
    Assertions.assertEquals(expected.getAchievement_name(), actual.getAchievement_name());
    Assertions.assertEquals(
        expected.getAchievement_description(), actual.getAchievement_description());
    Assertions.assertEquals(expected.getAwarded_by(), actual.getAwarded_by());
    Assertions.assertEquals(expected.getAward_date(), actual.getAward_date());
  }

  public static void assertEducationEquals(Education expected, Education actual) {
    Assertions.assertEquals(expected.getSchool_name(), actual.getSchool_name());
    Assertions.assertEquals(expected.getDegree(), actual.getDegree());
    Assertions.assertEquals(expected.getEducation_start_date(), actual.getEducation_start_date());
    Assertions.assertEquals(expected.getCity(), actual.getCity());
    Assertions.assertEquals(expected.getEnd_date(), actual.getEnd_date());
    Assertions.assertEquals(expected.getMajor(), actual.getMajor());
    Assertions.assertEquals(expected.getEducation_description(), actual.getEducation_description());
    Assertions.assertEquals(expected.getGrade(), actual.getGrade());
  }

  public static void assertEducationEquals(Education expected, EducationView actual) {
    Assertions.assertEquals(expected.getSchool_name(), actual.getSchool_name());
    Assertions.assertEquals(expected.getDegree(), actual.getDegree());
    Assertions.assertEquals(expected.getEducation_start_date(), actual.getEducation_start_date());
    Assertions.assertEquals(expected.getCity(), actual.getCity());
    Assertions.assertEquals(expected.getEnd_date(), actual.getEnd_date());
    Assertions.assertEquals(expected.getMajor(), actual.getMajor());
    Assertions.assertEquals(expected.getEducation_description(), actual.getEducation_description());
    Assertions.assertEquals(expected.getGrade(), actual.getGrade());
  }

  public static void assertExperienceEquals(Experience expected, Experience actual) {
    Assertions.assertEquals(expected.getCompany_name(), actual.getCompany_name());
    Assertions.assertEquals(expected.getJob_title(), actual.getJob_title());
    Assertions.assertEquals(expected.getEmployment_start(), actual.getEmployment_start());
    Assertions.assertEquals(expected.getEmployment_end(), actual.getEmployment_end());
    Assertions.assertEquals(
        expected.getExperience_description(), actual.getExperience_description());
    Assertions.assertEquals(expected.getCity(), actual.getCity());
  }

  public static void assertExperienceEquals(Experience expected, ExperienceView actual) {
    Assertions.assertEquals(expected.getCompany_name(), actual.getCompany_name());
    Assertions.assertEquals(expected.getJob_title(), actual.getJob_title());
    Assertions.assertEquals(expected.getEmployment_start(), actual.getEmployment_start());
    Assertions.assertEquals(expected.getEmployment_end(), actual.getEmployment_end());
    Assertions.assertEquals(
        expected.getExperience_description(), actual.getExperience_description());
    Assertions.assertEquals(expected.getCity(), actual.getCity());
  }
}
