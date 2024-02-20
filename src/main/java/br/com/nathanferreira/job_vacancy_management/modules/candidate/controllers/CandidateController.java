package br.com.nathanferreira.job_vacancy_management.modules.candidate.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nathanferreira.job_vacancy_management.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.nathanferreira.job_vacancy_management.modules.candidate.entities.CandidateEntity;
import br.com.nathanferreira.job_vacancy_management.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.nathanferreira.job_vacancy_management.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.nathanferreira.job_vacancy_management.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.nathanferreira.job_vacancy_management.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidate", description = "Candidate infos")
public class CandidateController {

  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;

  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;

  @Autowired
  private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

  @PostMapping("/")
  @Operation(summary = "Candidate registration", description = "Endpoint responsible for creating a new candidate")
  @ApiResponses({
    @ApiResponse(responseCode = "200", content = {
        @Content(schema = @Schema(implementation = CandidateEntity.class))
    }),
    @ApiResponse(responseCode = "400", description = "User already exists")
})
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
    try {
      var result = this.createCandidateUseCase.execute(candidateEntity);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/")
  @PreAuthorize("hasRole('CANDIDATE')")
  @Operation(summary = "Get candidate profile", description = "Endpoint responsible for return the candidade profile infos")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "User not found")
  })
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> get(HttpServletRequest request) {
    var idCandidate = request.getAttribute("candidate_id");

    try {
      var profile = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));

      return ResponseEntity.ok().body(profile);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/job")
  @PreAuthorize("hasRole('CANDIDATE')")
  @Operation(summary = "List of available job vacancies for candidate", description = "Endpoint responsible for listing all available job vacancies based on filter")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
      })
  })
  @SecurityRequirement(name = "jwt_auth")
  public List<JobEntity> findJobByFilter(@RequestParam String filter) {
    var jobs = this.listAllJobsByFilterUseCase.execute(filter);

    return jobs;
  }

}
