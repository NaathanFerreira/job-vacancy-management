package br.com.nathanferreira.job_vacancy_management.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateJobDTO {
  
  @Schema(example = "Javascript Jr Developer")
  private String description;

  @Schema(example = "GymPass", requiredMode = RequiredMode.REQUIRED)
  @NotBlank(message = "Field (benefits) is required")
  private String benefits;

  @Schema(example = "Junior", requiredMode = RequiredMode.REQUIRED)
  private String level;

}
