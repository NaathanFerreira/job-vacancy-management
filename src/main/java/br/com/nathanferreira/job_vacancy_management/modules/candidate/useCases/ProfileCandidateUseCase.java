package br.com.nathanferreira.job_vacancy_management.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.nathanferreira.job_vacancy_management.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.nathanferreira.job_vacancy_management.modules.candidate.entities.CandidateEntity;
import br.com.nathanferreira.job_vacancy_management.modules.candidate.repositories.CandidateRepository;

@Service
public class ProfileCandidateUseCase {

  @Autowired
  private CandidateRepository candidateRepository;

  public ProfileCandidateResponseDTO execute(UUID idCandidate) {

    var candidate = this.candidateRepository.findById(idCandidate).orElseThrow(() -> {
      throw new UsernameNotFoundException("User not found");
    });

    var profileCandidateResponseDTO = ProfileCandidateResponseDTO.builder()
        .id(idCandidate)
        .name(candidate.getName())
        .description(candidate.getDescription())
        .username(candidate.getUsername())
        .email(candidate.getEmail())
        .build();

    return profileCandidateResponseDTO;

  }

}
