package br.com.nathanferreira.job_vacancy_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@OpenAPIDefinition (
	info = @Info(
		title = "Job Vacancy Management",
		description = "API responsable for manage job vacancies",
		version = "1"
	)
)
@SecurityScheme(name = "jwt_auth", scheme = "bearer", bearerFormat = "JWT", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class JobVacancyManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobVacancyManagementApplication.class, args);
	}

}
