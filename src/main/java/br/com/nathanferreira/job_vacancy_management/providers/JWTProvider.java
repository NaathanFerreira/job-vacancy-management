package br.com.nathanferreira.job_vacancy_management.providers;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class JWTProvider {

  // from application.properties
  @Value("${security.token.secret}")
  private String secretKey;

  public String validateToken(String token) {
    token = token.replace("Bearer ", "");

    try {
      Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
      
      var subject = JWT.require(algorithm).build().verify(token).getSubject();
      return subject;
    } catch (JWTVerificationException e) {
      e.printStackTrace();
      return "";
    }
  }

  public String generateToken(String subject) {
    Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
    return JWT.create()
        .withIssuer("javacancy")
        .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
        .withSubject(subject)
        .sign(algorithm);
  }

}
