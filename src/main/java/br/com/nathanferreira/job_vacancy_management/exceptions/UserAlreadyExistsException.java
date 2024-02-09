package br.com.nathanferreira.job_vacancy_management.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
  public UserAlreadyExistsException() {
    super("User already exists");
  }
}
