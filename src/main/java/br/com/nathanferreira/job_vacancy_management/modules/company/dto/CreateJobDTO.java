package br.com.nathanferreira.job_vacancy_management.modules.company.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateJobDTO {
  
  private String description;

  @NotBlank(message = "Field (benefits) is required")
  private String benefits;
  private String level;

}
