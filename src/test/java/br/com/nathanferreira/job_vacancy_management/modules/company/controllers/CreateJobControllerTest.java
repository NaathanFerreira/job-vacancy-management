package br.com.nathanferreira.job_vacancy_management.modules.company.controllers;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.nathanferreira.job_vacancy_management.exceptions.CompanyNotFoundException;
import br.com.nathanferreira.job_vacancy_management.modules.company.dto.CreateJobDTO;
import br.com.nathanferreira.job_vacancy_management.modules.company.entities.CompanyEntity;
import br.com.nathanferreira.job_vacancy_management.modules.company.repositories.CompanyRepository;
import br.com.nathanferreira.job_vacancy_management.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private CompanyRepository companyRepository;

  @Before
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  @DisplayName("Should be able to create a new job")
  public void should_be_able_to_create_a_new_job() throws Exception {

    var company = CompanyEntity.builder()
        .description("COMPANY_DESCRIPTION")
        .email("company@hotmail.com")
        .password("12345678910")
        .username("COMPANY_USERNAME")
        .name("COMPANY_NAME")
        .build();

    company = this.companyRepository.saveAndFlush(company);

    var createJobDTO = CreateJobDTO.builder()
        .benefits("BENEFITS_TEST")
        .description("DESCRIPTION_TEST")
        .level("LEVEL_TEST")
        .build();

    mvc.perform(MockMvcRequestBuilders.post("/company/job/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtils.objectToJson(createJobDTO))
        .header("Authorization", TestUtils.generateToken(company.getId(), "javacancy_@123#")))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @DisplayName("Should not be able to create a new job if company not found")
  public void should_not_be_able_to_create_a_new_job_if_company_not_found() throws Exception {

    var createdJobDTO = CreateJobDTO.builder()
        .benefits("BENEFITS_TEST")
        .description("DESCRIPTION_TEST")
        .level("LEVEL_TEST")
        .build();

    mvc.perform(MockMvcRequestBuilders.post("/company/job/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtils.objectToJson(createdJobDTO))
        .header("Authorization", TestUtils.generateToken(UUID.randomUUID(), "javacancy_@123#")))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

}
