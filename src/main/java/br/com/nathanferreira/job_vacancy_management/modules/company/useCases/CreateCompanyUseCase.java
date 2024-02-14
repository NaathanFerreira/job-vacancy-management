package br.com.nathanferreira.job_vacancy_management.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nathanferreira.job_vacancy_management.exceptions.UserAlreadyExistsException;
import br.com.nathanferreira.job_vacancy_management.modules.company.entities.CompanyEntity;
import br.com.nathanferreira.job_vacancy_management.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyUseCase {

  @Autowired
  private CompanyRepository companyRepository;

  public CompanyEntity execute(CompanyEntity companyEntity) {
    this.companyRepository
        .findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
        .ifPresent((user) -> {
          throw new UserAlreadyExistsException();
        });

    return this.companyRepository.save(companyEntity);
  }

}