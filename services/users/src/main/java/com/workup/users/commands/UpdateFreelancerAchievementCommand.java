package com.workup.users.commands;

import com.workup.users.commands.requests.UpdateFreelancerAchievementRequest;
import com.workup.users.commands.responses.UpdateFreelancerAchievementResponse;
import com.workup.users.db.Achievement;
import com.workup.users.db.Freelancer;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class UpdateFreelancerAchievementCommand extends UserCommand<UpdateFreelancerAchievementRequest, UpdateFreelancerAchievementResponse> {
    @Override
    public UpdateFreelancerAchievementResponse Run(UpdateFreelancerAchievementRequest request) {
        Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getFreelancer_id());
        if (freelancerOptional.isEmpty())
            return UpdateFreelancerAchievementResponse.builder().withSuccess(false).build();
        Freelancer freelancer = freelancerOptional.get();
        updateAchievement(request.getUpdatedAchievement());
        freelancerRepository.save(freelancer);
        return UpdateFreelancerAchievementResponse.builder().withSuccess(true).withFreelancer(freelancer).build();
    }

    public void updateAchievement(Achievement updatedAchievement) {
        String id = updatedAchievement.getId().toString();
        Optional<Achievement> achievementOptional = achievementRepository.findById(id);
        if (achievementOptional.isEmpty()) {
            ResponseEntity.notFound().build();
            return;
        }
        Achievement existingAchievement = achievementOptional.get();
        BeanUtils.copyProperties(updatedAchievement, existingAchievement, "id");
        achievementRepository.save(existingAchievement);
        ResponseEntity.ok(existingAchievement);
    }
}
