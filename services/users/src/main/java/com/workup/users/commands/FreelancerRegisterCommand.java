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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FreelancerRegisterCommand
    extends UserCommand<FreelancerRegisterRequest, SignUpAndInResponse> {
  private static final Logger logger = LogManager.getLogger(FreelancerRegisterCommand.class);

  @Override
  public SignUpAndInResponse Run(FreelancerRegisterRequest request) {
    logger.info("[i] Registering Freelancer with Email: " + request.getEmail());
    if (Objects.isNull(request.getEmail())
        || Objects.isNull(request.getPassword())
        || Objects.isNull(request.getFullName())) {
      logger.error("[x] Missing Required Fields");
      return SignUpAndInResponse.builder()
          .withStatusCode(HttpStatusCode.BAD_REQUEST)
          .withSuccess(false)
          .build();
    }
    try {
      // check if registered already as client
      if (clientRepository.findByEmail(request.getEmail()).isPresent()) {
        logger.error("[x] User with email" + request.getEmail() + " already registered as client");
        return SignUpAndInResponse.builder()
            .withStatusCode(HttpStatusCode.BAD_REQUEST)
            .withSuccess(false)
            .withErrorMessage("User already registered as client")
            .build();
      }
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
      logger.error("[x] Error Registering Freelancer: " + e.getMessage());
      return SignUpAndInResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withErrorMessage(e.getMessage())
          .withSuccess(false)
          .build();
    }
  }
}
