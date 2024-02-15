package br.com.nathanferreira.job_vacancy_management.modules.candidate.useCases;

import java.time.Duration;
import java.time.Instant;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.nathanferreira.job_vacancy_management.modules.candidate.CandidateRepository;
import br.com.nathanferreira.job_vacancy_management.modules.candidate.dto.AuthCandidateDTO;
import br.com.nathanferreira.job_vacancy_management.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.nathanferreira.job_vacancy_management.providers.JWTCandidateProvider;

@Service
public class AuthCandidateUseCase {

  @Autowired
  private JWTCandidateProvider jwtProvider;

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public AuthCandidateResponseDTO execute(AuthCandidateDTO credentials) throws AuthenticationException {

    var candidate = this.candidateRepository.findByUsername(credentials.username())
        .orElseThrow(() -> {
          throw new UsernameNotFoundException("Incorrect username/password");
        });

    var passwordMatches = this.passwordEncoder.matches(credentials.password(), candidate.getPassword());

    if (!passwordMatches) {
      throw new AuthenticationException();
    }

    var token = this.jwtProvider.generateToken(candidate.getId().toString());

    var expiresIn = Instant.now().plus(Duration.ofMinutes(10));
    var authCandidateResponse = AuthCandidateResponseDTO.builder()
        .access_token(token)
        .expires_in(expiresIn.toEpochMilli())
        .build();

    return authCandidateResponse;
  }

}
