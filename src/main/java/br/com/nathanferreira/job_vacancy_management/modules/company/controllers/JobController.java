package br.com.nathanferreira.job_vacancy_management.modules.company.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nathanferreira.job_vacancy_management.modules.company.entities.JobEntity;
import br.com.nathanferreira.job_vacancy_management.modules.company.useCases.CreateJobUseCase;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/job")
public class JobController {

  @Autowired
  private CreateJobUseCase createJobUseCase;

  @PostMapping("/")
  public ResponseEntity<Object> create(@Valid @RequestBody JobEntity entity) {

    var result = this.createJobUseCase.execute(entity);

    return ResponseEntity.ok().body(result);
  }

}
