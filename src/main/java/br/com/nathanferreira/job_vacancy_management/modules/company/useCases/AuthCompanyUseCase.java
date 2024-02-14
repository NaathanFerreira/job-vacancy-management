package br.com.nathanferreira.job_vacancy_management.modules.company.useCases;

import java.time.Duration;
import java.time.Instant;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.nathanferreira.job_vacancy_management.modules.company.dto.AuthCompanyDTO;
import br.com.nathanferreira.job_vacancy_management.modules.company.repositories.CompanyRepository;
import br.com.nathanferreira.job_vacancy_management.providers.JWTProvider;

@Service
public class AuthCompanyUseCase {

  @Autowired 
  private JWTProvider jwtProvider;

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public String execute(AuthCompanyDTO credentials) throws AuthenticationException {
    var company = this.companyRepository.findByUsername(credentials.getUsername()).orElseThrow(() -> {
      throw new UsernameNotFoundException("Incorrect username/password");
    });

    var passwordMatches = this.passwordEncoder.matches(credentials.getPassword(), company.getPassword());

    if (!passwordMatches) {
      throw new AuthenticationException();
    }

    var token = this.jwtProvider.generateToken(company.getId().toString());

    return token;

  }

}
