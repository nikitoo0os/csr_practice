package com.vyatsu.practiceCSR.config.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.vyatsu.practiceCSR.dto.auth.UserAuthDto;
import com.vyatsu.practiceCSR.entity.api.User;
import com.vyatsu.practiceCSR.service.api.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class UserAuthenticationProvider {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    private final UserService userService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String email) {
        Date now = new Date();
        Date validity = new Date(now.getTime() +  604800000); // 7 days

        User user = userService.getUserByEmail(email);

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withIssuer(email)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("isAdmin", user.getIsAdmin())
                .sign(algorithm);
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        DecodedJWT decoded = verifier.verify(token);

        UserAuthDto user = userService.findByEmail(decoded.getIssuer());

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

}
