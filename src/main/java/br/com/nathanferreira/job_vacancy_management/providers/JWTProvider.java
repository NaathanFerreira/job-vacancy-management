package br.com.nathanferreira.job_vacancy_management.providers;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTProvider {

  @Value("${security.token.secret}")
  private String secretKey;

  public DecodedJWT validateToken(String token) {
    token = token.replace("Bearer ", "");

    try {
      Algorithm algorithm = Algorithm.HMAC256(this.secretKey);

      var tokenDecoded = JWT.require(algorithm).build().verify(token);
      return tokenDecoded;
    } catch (JWTVerificationException e) {
      return null;
    }
  }

  public String generateToken(String subject) {
    Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
    return JWT.create()
        .withIssuer("javacancy")
        .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
        .withSubject(subject)
        .withClaim("roles", Arrays.asList("COMPANY"))
        .sign(algorithm);
  }

}
