package br.com.nathanferreira.job_vacancy_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition (
	info = @Info(
		title = "Job Vacancy Management",
		description = "API responsable for manage job vacancies",
		version = "1"
	)
)
public class JobVacancyManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobVacancyManagementApplication.class, args);
	}

}
