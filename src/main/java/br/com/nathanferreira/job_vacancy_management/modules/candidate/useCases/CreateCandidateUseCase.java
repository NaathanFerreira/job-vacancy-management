package br.com.nathanferreira.job_vacancy_management.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nathanferreira.job_vacancy_management.exceptions.UserAlreadyExistsException;
import br.com.nathanferreira.job_vacancy_management.modules.candidate.CandidateEntity;
import br.com.nathanferreira.job_vacancy_management.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateUseCase {

  @Autowired
  private CandidateRepository candidateRepository;

  public CandidateEntity execute(CandidateEntity candidateEntity) {
    this.candidateRepository
        .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
        .ifPresent((user) -> {
          throw new UserAlreadyExistsException();
        });

    return this.candidateRepository.save(candidateEntity);
  }

}
