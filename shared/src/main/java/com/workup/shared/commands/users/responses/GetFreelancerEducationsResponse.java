package com.workup.shared.commands.users.responses;

import com.workup.shared.commands.CommandResponse;
import com.workup.shared.views.users.EducationView;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class GetFreelancerEducationsResponse extends CommandResponse {
  List<EducationView> educations;
}
