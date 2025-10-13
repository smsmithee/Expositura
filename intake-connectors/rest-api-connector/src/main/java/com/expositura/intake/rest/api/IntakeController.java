package com.expositura.intake.rest.api;

import com.expositura.intake.rest.api.model.LegacyIntakeParsedResponse;
import com.expositura.intake.rest.api.model.LegacyIntakeRequest;
import com.expositura.intake.rest.api.model.LegacyIntakeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Intake Rest Connector API's
 */
@RestController
public class IntakeController implements IntakeApi {

  @Override
  public ResponseEntity<LegacyIntakeParsedResponse> legacyIntakeWithParsedResponse(LegacyIntakeRequest legacyIntakeRequest) {
    return IntakeApi.super.legacyIntakeWithParsedResponse(legacyIntakeRequest); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
  }

  @Override
  public ResponseEntity<LegacyIntakeResponse> legacyIntake(LegacyIntakeRequest legacyIntakeRequest) {
    return IntakeApi.super.legacyIntake(legacyIntakeRequest); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
  }
  
  
}
