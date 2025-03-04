//package ru.kolosov.CRUD.security;
//
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.time.ZonedDateTime;
//import java.util.Date;
//
//
//@Component
//public class Jwt {
//    @Value("${JWT.secret}")
//    private String secret;
//
//    public String generateToken(String login) {
//        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());
//
//        return JWT.create()
//                .withSubject("User Details")
//                .withClaim("login", login)
//                .withIssuedAt(new Date())
//                .withIssuer("Alex Kolosov")
//                .withExpiresAt(expirationDate)
//                .sign(Algorithm.HMAC256(secret));
//    }
//
//    public String validateToken(String token) throws JWTVerificationException {
//        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
//                .withSubject("User Details")
//                .withIssuer("Alex Kolosov")
//                .build();
//        DecodedJWT decodedJWT = verifier.verify(token);
//        return decodedJWT.getClaim("login").asString();
//    }
//}
