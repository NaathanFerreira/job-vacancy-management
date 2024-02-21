package br.com.nathanferreira.job_vacancy_management.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nathanferreira.job_vacancy_management.exceptions.CompanyNotFoundException;
import br.com.nathanferreira.job_vacancy_management.modules.company.entities.JobEntity;
import br.com.nathanferreira.job_vacancy_management.modules.company.repositories.CompanyRepository;
import br.com.nathanferreira.job_vacancy_management.modules.company.repositories.JobRepository;

@Service
public class CreateJobUseCase {

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private CompanyRepository companyRepository;

  public JobEntity execute(JobEntity jobEntity) {
    this.companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(() -> {
      throw new CompanyNotFoundException();
    });

    return this.jobRepository.save(jobEntity);
  }

}
