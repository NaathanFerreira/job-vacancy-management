package br.com.nathanferreira.job_vacancy_management.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nathanferreira.job_vacancy_management.exceptions.JobNotFoundException;
import br.com.nathanferreira.job_vacancy_management.exceptions.UserNotFoundException;
import br.com.nathanferreira.job_vacancy_management.modules.candidate.entities.ApplyJobEntity;
import br.com.nathanferreira.job_vacancy_management.modules.candidate.repositories.ApplyJobRepository;
import br.com.nathanferreira.job_vacancy_management.modules.candidate.repositories.CandidateRepository;
import br.com.nathanferreira.job_vacancy_management.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {

  @Autowired
  private ApplyJobRepository applyJobRepository;

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private JobRepository jobRepository;

  public ApplyJobEntity execute(UUID idCandidate, UUID idJob) {

    this.candidateRepository.findById(idCandidate).orElseThrow(() -> {
      throw new UserNotFoundException();
    });

    this.jobRepository.findById(idJob).orElseThrow(() -> {
      throw new JobNotFoundException();
    });

    var applyJob = ApplyJobEntity.builder()
        .candidateId(idCandidate)
        .jobId(idJob)
        .build();

    return this.applyJobRepository.save(applyJob);

  }

}
