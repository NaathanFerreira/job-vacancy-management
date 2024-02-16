package br.com.nathanferreira.job_vacancy_management.modules.candidate.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nathanferreira.job_vacancy_management.modules.company.entities.JobEntity;
import br.com.nathanferreira.job_vacancy_management.modules.company.repositories.JobRepository;

@Service
public class ListAllJobsByFilterUseCase {
  
  @Autowired
  private JobRepository jobRepository;

  public List<JobEntity> execute(String filter) {
    var jobs = jobRepository.findByDescriptionContaining(filter);

    return jobs;
  }

}
