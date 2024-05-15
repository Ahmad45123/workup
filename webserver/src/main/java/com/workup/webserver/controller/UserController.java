package com.workup.webserver.controller;

import com.workup.shared.commands.CommandRequest;
import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.users.requests.*;
import com.workup.shared.commands.users.responses.*;
import com.workup.shared.enums.ServiceQueueNames;
import com.workup.webserver.config.JwtService;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
  @Autowired JwtService jwtService;
  @Autowired AmqpTemplate rabbitTemplate;

  private ResponseEntity<AuthResponse> getAuthResponse(CommandRequest request) {
    SignUpAndInResponse response =
        (SignUpAndInResponse)
            rabbitTemplate.convertSendAndReceive(ServiceQueueNames.USERS, request);
    System.out.println(response.getStatusCode().getValue());
    if (response.isSuccess()) {
      String token =
          jwtService.generateToken(
              response.getUserName(), response.getUserId(), response.getUserType().toString());
      return ResponseEntity.ok(AuthResponse.builder().authToken(token).build());
    }
    return ResponseEntity.status(response.getStatusCode().getValue())
        .body(AuthResponse.builder().errorMessage(response.getErrorMessage()).build());
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
    return getAuthResponse(request);
  }

  @PostMapping("/clients/register")
  public ResponseEntity<AuthResponse> registerAsClient(@RequestBody ClientRegisterRequest request) {
    return getAuthResponse(request);
  }

  @PostMapping("/freelancers/register")
  public ResponseEntity<AuthResponse> registerAsFreelancer(
      @RequestBody FreelancerRegisterRequest request) {
    return getAuthResponse(request);
  }

  private <Response extends CommandResponse> ResponseEntity<Response> userDataCRUD(
      String userId, CommandRequest request) {
    request.setUserId(userId);
    Response response =
        (Response) rabbitTemplate.convertSendAndReceive(ServiceQueueNames.USERS, request);
    return ResponseEntity.status(response.getStatusCode().getValue()).body(response);
  }

  @PostMapping("/freelancer/achievements")
  public ResponseEntity<AddFreelancerAchievementResponse> postFreelancerAchievements(
      @RequestAttribute(name = "userId") String userId,
      @RequestBody AddFreelancerAchievementRequest request) {
    return userDataCRUD(userId, request);
  }

  @PostMapping("/freelancer/education")
  public ResponseEntity<AddFreelancerEducationResponse> postFreelancerEducation(
      @RequestAttribute(name = "userId") String userId,
      @RequestBody AddFreelancerEducationRequest request) {
    return userDataCRUD(userId, request);
  }

  @PostMapping("/freelancer/experience")
  public ResponseEntity<AddFreelancerExperienceResponse> postFreelancerExperience(
      @RequestAttribute(name = "userId") String userId,
      @RequestBody AddFreelancerExperienceRequest request) {
    return userDataCRUD(userId, request);
  }

  @PostMapping("/freelancer/languages")
  public ResponseEntity<AddFreelancerLanguageResponse> postFreelancerLanguages(
      @RequestAttribute(name = "userId") String userId,
      @RequestBody AddFreelancerLanguageRequest request) {
    return userDataCRUD(userId, request);
  }

  @PostMapping("/freelancer/skills")
  public ResponseEntity<AddFreelancerSkillResponse> postFreelancerSkills(
      @RequestAttribute(name = "userId") String userId,
      @RequestBody AddFreelancerSkillRequest request) {
    return userDataCRUD(userId, request);
  }

  @GetMapping("/client/photo")
  public ResponseEntity<ClientGetPhotoResponse> getClientPhoto(
      @RequestAttribute(name = "userId") String userId) {
    ClientGetPhotoRequest request = ClientGetPhotoRequest.builder().build();
    return userDataCRUD(userId, request);
  }

  @GetMapping("/client/profile")
  public ResponseEntity<ClientGetProfileResponse> getClientProfile(
      @RequestAttribute(name = "userId") String userId) {
    ClientGetProfileRequest request = ClientGetProfileRequest.builder().build();
    return userDataCRUD(userId, request);
  }

  @PostMapping("/client/photo")
  public ResponseEntity<ClientSetPhotoResponse> postClientPhoto(
      @RequestAttribute(name = "userId") String userId,
      @RequestBody ClientSetPhotoRequest request) {
    return userDataCRUD(userId, request);
  }

  @PostMapping("/client/profile")
  public ResponseEntity<ClientSetProfileResponse> postClientProfile(
      @RequestAttribute(name = "userId") String userId,
      @RequestBody ClientSetProfileRequest request) {
    return userDataCRUD(userId, request);
  }

  @GetMapping("/freelancer/photo")
  public ResponseEntity<FreelancerGetPhotoResponse> getFreelancerPhoto(
      @RequestAttribute(name = "userId") String userId) {
    FreelancerGetPhotoRequest request = FreelancerGetPhotoRequest.builder().build();
    return userDataCRUD(userId, request);
  }

  @GetMapping("/freelancer/profile_briefs")
  public ResponseEntity<FreelancerGetProfileBriefResponse> getFreelancerProfileBriefs(
      @RequestAttribute(name = "userId") String userId) {
    FreelancerGetProfileBriefRequest request = FreelancerGetProfileBriefRequest.builder().build();
    return userDataCRUD(userId, request);
  }

  @GetMapping("/freelancer/profile")
  public ResponseEntity<FreelancerGetProfileResponse> getFreelancerProfile(
      @RequestAttribute(name = "userId") String userId) {
    FreelancerGetProfileRequest request = FreelancerGetProfileRequest.builder().build();
    return userDataCRUD(userId, request);
  }

  @GetMapping("/freelancer/resume")
  public ResponseEntity<FreelancerGetResumeResponse> getFreelancerResume(
      @RequestAttribute(name = "userId") String userId) {
    FreelancerGetResumeRequest request = FreelancerGetResumeRequest.builder().build();
    return userDataCRUD(userId, request);
  }

  @PostMapping("/freelancer/photo")
  public ResponseEntity<FreelancerSetPhotoResponse> postFreelancerPhoto(
      @RequestAttribute(name = "userId") String userId,
      @RequestBody FreelancerSetPhotoRequest request) {
    return userDataCRUD(userId, request);
  }

  @PostMapping("/freelancer/profile")
  public ResponseEntity<FreelancerSetProfileResponse> postFreelancerProfile(
      @RequestAttribute(name = "userId") String userId,
      @RequestBody FreelancerSetProfileRequest request) {
    return userDataCRUD(userId, request);
  }

  @PostMapping("/freelancer/resume")
  public ResponseEntity<FreelancerSetResumeResponse> postFreelancerResume(
      @RequestAttribute(name = "userId") String userId,
      @RequestBody FreelancerSetResumeRequest request) {
    return userDataCRUD(userId, request);
  }

  @GetMapping("/freelancer/achievements")
  public ResponseEntity<GetFreelancerAchievementsResponse> getFreelancerAchievements(
      @RequestAttribute(name = "userId") String userId) {
    GetFreelancerAchievementsRequest request = GetFreelancerAchievementsRequest.builder().build();
    return userDataCRUD(userId, request);
  }

  @GetMapping("/freelancer/education")
  public ResponseEntity<GetFreelancerEducationsResponse> getFreelancerEducations(
      @RequestAttribute(name = "userId") String userId) {
    GetFreelancerEducationsRequest request = GetFreelancerEducationsRequest.builder().build();
    return userDataCRUD(userId, request);
  }

  @GetMapping("/freelancer/experience")
  public ResponseEntity<GetFreelancerExperiencesResponse> getFreelancerExperiences(
      @RequestAttribute(name = "userId") String userId) {
    GetFreelancerExperiencesRequest request = GetFreelancerExperiencesRequest.builder().build();
    return userDataCRUD(userId, request);
  }

  @GetMapping("/freelancer/languages")
  public ResponseEntity<GetFreelancerLanguagesResponse> getFreelancerLanguages(
      @RequestAttribute(name = "userId") String userId) {
    GetFreelancerLanguagesRequest request = GetFreelancerLanguagesRequest.builder().build();
    return userDataCRUD(userId, request);
  }

  @GetMapping("/freelancer/skills")
  public ResponseEntity<GetFreelancerSkillsResponse> getFreelancerSkills(
      @RequestAttribute(name = "userId") String userId) {
    GetFreelancerSkillsRequest request = GetFreelancerSkillsRequest.builder().build();
    return userDataCRUD(userId, request);
  }

  @DeleteMapping("/freelancer/achievements")
  public ResponseEntity<RemoveFreelancerAchievementResponse> deleteFreelancerAchievements(
      @RequestAttribute(name = "userId") String userId, @RequestParam String achievementId) {
    RemoveFreelancerAchievementRequest request =
        RemoveFreelancerAchievementRequest.builder().withAchievementId(achievementId).build();
    return userDataCRUD(userId, request);
  }

  @DeleteMapping("/freelancer/education")
  public ResponseEntity<RemoveFreelancerEducationResponse> deleteFreelancerEducation(
      @RequestAttribute(name = "userId") String userId, @RequestParam String educationId) {
    RemoveFreelancerEducationRequest request =
        RemoveFreelancerEducationRequest.builder().withEducation_id(educationId).build();
    return userDataCRUD(userId, request);
  }

  @DeleteMapping("/freelancer/experience")
  public ResponseEntity<RemoveFreelancerExperienceResponse> deleteFreelancerExperience(
      @RequestAttribute(name = "userId") String userId, @RequestParam String experienceId) {
    RemoveFreelancerExperienceRequest request =
        RemoveFreelancerExperienceRequest.builder().withExperience_id(experienceId).build();
    return userDataCRUD(userId, request);
  }

  @DeleteMapping("/freelancer/languages")
  public ResponseEntity<RemoveFreelancerLanguageResponse> deleteFreelancerLanguages(
      @RequestAttribute(name = "userId") String userId, @RequestParam String languageToRemove) {
    RemoveFreelancerLanguageRequest request =
        RemoveFreelancerLanguageRequest.builder().withLanguageToRemove(languageToRemove).build();
    return userDataCRUD(userId, request);
  }

  @DeleteMapping("/freelancer/skills")
  public ResponseEntity<RemoveFreelancerSkillResponse> deleteFreelancerSkills(
      @RequestAttribute(name = "userId") String userId, @RequestParam String skillToRemove) {
    RemoveFreelancerSkillRequest request =
        RemoveFreelancerSkillRequest.builder().withSkillToRemove(skillToRemove).build();
    return userDataCRUD(userId, request);
  }

  @GetMapping("path")
  @PutMapping("/freelancer/achievements")
  public ResponseEntity<UpdateFreelancerAchievementResponse> putFreelancerAchievements(
      @RequestAttribute(name = "userId") String userId,
      @RequestBody UpdateFreelancerAchievementRequest request) {
    return userDataCRUD(userId, request);
  }

  @PutMapping("/freelancer/education")
  public ResponseEntity<UpdateFreelancerEducationResponse> putFreelancerEducation(
      @RequestAttribute(name = "userId") String userId,
      @RequestBody UpdateFreelancerEducationRequest request) {
    return userDataCRUD(userId, request);
  }

  @PutMapping("/freelancer/experience")
  public ResponseEntity<UpdateFreelancerExperienceResponse> putFreelancerExperience(
      @RequestAttribute(name = "userId") String userId,
      @RequestBody UpdateFreelancerExperienceRequest request) {
    return userDataCRUD(userId, request);
  }
}

/** InnerUserController */
@Builder
@Jacksonized
@Getter
class AuthResponse {
  String authToken;
  String errorMessage;
}
