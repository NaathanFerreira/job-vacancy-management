package br.com.nathanferreira.job_vacancy_management.modules.company.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nathanferreira.job_vacancy_management.modules.company.dto.CreateJobDTO;
import br.com.nathanferreira.job_vacancy_management.modules.company.entities.JobEntity;
import br.com.nathanferreira.job_vacancy_management.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/company/job")
public class JobController {

  @Autowired
  private CreateJobUseCase createJobUseCase;

  @PostMapping("/")
  @PreAuthorize("hasRole('COMPANY')")
  public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO jobDTO, HttpServletRequest request) {

    // attribute defined in SecurityFilter
    var companyId = request.getAttribute("company_id");

    var jobEntity = JobEntity.builder()
        .companyId(UUID.fromString(companyId.toString()))
        .benefits(jobDTO.getBenefits())
        .description(jobDTO.getDescription())
        .level(jobDTO.getLevel())
        .build();

    var result = this.createJobUseCase.execute(jobEntity);

    return ResponseEntity.ok().body(result);
  }

}
