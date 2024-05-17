package com.workup.users.commands;

import static com.workup.users.commands.utils.PasswordHasher.hashPassword;

import com.workup.shared.commands.payments.wallet.requests.CreateWalletRequest;
import com.workup.shared.commands.users.requests.FreelancerRegisterRequest;
import com.workup.shared.commands.users.responses.SignUpAndInResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.ServiceQueueNames;
import com.workup.shared.enums.users.UserType;
import com.workup.users.db.Freelancer;
import java.util.Objects;

public class FreelancerRegisterCommand
    extends UserCommand<FreelancerRegisterRequest, SignUpAndInResponse> {

  @Override
  public SignUpAndInResponse Run(FreelancerRegisterRequest request) {
    if (Objects.isNull(request.getEmail())
        || Objects.isNull(request.getPassword())
        || Objects.isNull(request.getFullName())) {
      return SignUpAndInResponse.builder()
          .withStatusCode(HttpStatusCode.BAD_REQUEST)
          .withSuccess(false)
          .build();
    }
    try {
      Freelancer freelancer =
          Freelancer.builder()
              .withEmail(request.getEmail())
              .withPassword_hash(hashPassword(request.getPassword()))
              .withFullName(request.getFullName())
              .withJob_title(request.getJobTitle())
              .withCity(request.getCity())
              .build();
      Freelancer savedFreelancer = freelancerRepository.save(freelancer);
      // create wallet
      CreateWalletRequest createWalletRequest =
          CreateWalletRequest.builder()
              .withUserId(savedFreelancer.getId().toString())
              .withFreelancerId(savedFreelancer.getId().toString())
              .build();
      rabbitTemplate.convertSendAndReceive(ServiceQueueNames.PAYMENTS, createWalletRequest);

      return SignUpAndInResponse.builder()
          .withSuccess(true)
          .withUserName(savedFreelancer.getEmail())
          .withUserId(savedFreelancer.getId().toString())
          .withUserType(UserType.FREELANCER)
          .withStatusCode(HttpStatusCode.OK)
          .build();
    } catch (Exception e) {
      return SignUpAndInResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withErrorMessage(e.getMessage())
          .withSuccess(false)
          .build();
    }
  }
}
