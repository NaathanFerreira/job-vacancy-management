package br.com.nathanferreira.job_vacancy_management.modules.candidate.useCases;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.nathanferreira.job_vacancy_management.exceptions.JobNotFoundException;
import br.com.nathanferreira.job_vacancy_management.exceptions.UserNotFoundException;
import br.com.nathanferreira.job_vacancy_management.modules.candidate.entities.ApplyJobEntity;
import br.com.nathanferreira.job_vacancy_management.modules.candidate.entities.CandidateEntity;
import br.com.nathanferreira.job_vacancy_management.modules.candidate.repositories.ApplyJobRepository;
import br.com.nathanferreira.job_vacancy_management.modules.candidate.repositories.CandidateRepository;
import br.com.nathanferreira.job_vacancy_management.modules.company.entities.JobEntity;
import br.com.nathanferreira.job_vacancy_management.modules.company.repositories.JobRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

  @InjectMocks
  private ApplyJobCandidateUseCase applyJobCandidateUseCase;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private JobRepository jobRepository;

  @Mock
  private ApplyJobRepository applyJobRepository;

  @Test
  @DisplayName("Should not be able to apply job with candidate not found")
  public void should_not_be_able_to_aplly_job_with_candidate_not_found() {
    try {
      this.applyJobCandidateUseCase.execute(null, null);
    } catch (Exception e) {
      assertThat(e).isInstanceOf(UserNotFoundException.class);
    }
  }

  @Test
  @DisplayName("Should not be able to apply job with job not found")
  public void should_not_be_able_to_aplly_job_with_job_not_found() {
    var idCandidate = UUID.randomUUID();

    var candidate = new CandidateEntity();
    candidate.setId(idCandidate);

    when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

    try {
      this.applyJobCandidateUseCase.execute(idCandidate, null);
    } catch (Exception e) {
      assertThat(e).isInstanceOf(JobNotFoundException.class);
    }
  }

  @Test
  @DisplayName("Should be able to apply to a job")
  public void should_be_able_to_create_a_new_apply_job() {
    var idCandidate = UUID.randomUUID();

    var idJob = UUID.randomUUID();

    var applyJob = ApplyJobEntity.builder()
        .candidateId(idCandidate)
        .jobId(idJob)
        .build();

    var applyJobCreated = ApplyJobEntity.builder().id(UUID.randomUUID()).build();

    when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));
    when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobEntity()));

    when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

    var result = applyJobCandidateUseCase.execute(idCandidate, idJob);

    assertThat(result).hasFieldOrProperty("id");
    assertNotNull(result.getId());

  }

}
