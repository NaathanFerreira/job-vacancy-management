package br.com.nathanferreira.job_vacancy_management.modules.candidate;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

// create getter and setters for all attributes
@Data
public class CandidateEntity {

  private UUID id;
  private String name;

  @NotBlank()
  @Pattern(regexp = "\\S+", message = "Field (username) cannot have spaces")
  private String username;

  @Email(message = "Field (email) must be a valid email")
  private String email;

  @Length(min = 6, max = 10)
  private String password;
  private String description;
  private String curriculum;

}
