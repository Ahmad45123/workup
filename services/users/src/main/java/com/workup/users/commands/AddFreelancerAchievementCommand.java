package com.workup.users.commands;
import com.workup.users.commands.requests.AddFreelancerAchievementRequest;
import com.workup.users.commands.responses.AddFreelancerAchievementResponse;
import com.workup.users.db.Achievement;
import com.workup.users.db.Freelancer;
import java.util.Optional;

public class AddFreelancerAchievementCommand extends UserCommand<AddFreelancerAchievementRequest, AddFreelancerAchievementResponse> {
    @Override
    public AddFreelancerAchievementResponse Run(AddFreelancerAchievementRequest request) {
        Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getFreelancer_id());
        if (freelancerOptional.isEmpty())
            return AddFreelancerAchievementResponse.builder().withSuccess(false).build();
        Freelancer freelancer = freelancerOptional.get();
        Achievement newAchievement = achievementRepository.save(request.getNewAchievement());
        freelancer.getAchievements().add(newAchievement);
        freelancerRepository.save(freelancer);
        return AddFreelancerAchievementResponse.builder().withSuccess(true).withFreelancer(freelancer).build();
    }
}
