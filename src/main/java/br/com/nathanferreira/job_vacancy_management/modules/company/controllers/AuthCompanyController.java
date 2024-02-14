package br.com.nathanferreira.job_vacancy_management.modules.company.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nathanferreira.job_vacancy_management.modules.company.dto.AuthCompanyDTO;
import br.com.nathanferreira.job_vacancy_management.modules.company.useCases.AuthCompanyUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthCompanyController {

  @Autowired
  private AuthCompanyUseCase authCompanyUseCase;

  @PostMapping("/company")
  public ResponseEntity<Object> signIn(@RequestBody AuthCompanyDTO credentials) {
    try {
      var result = this.authCompanyUseCase.execute(credentials);

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }

}
