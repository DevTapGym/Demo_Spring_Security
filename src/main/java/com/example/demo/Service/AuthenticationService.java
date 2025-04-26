package com.example.demo.Service;

import com.example.demo.DTO.Request.ReqAuthentication;
import com.example.demo.DTO.Request.ReqIntrospect;
import com.example.demo.DTO.Response.ResAuthentication;
import com.example.demo.DTO.Response.ResIntrospect;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @NonFinal
    private static final String SIGNER_KEY = "BGwmykSuANkzM1mPje22W4RkF2MzbtgmTYSoT4xQx+Ca8/m+hfXru3qEMzHGEL0t";

    public ResAuthentication authenticate(ReqAuthentication reqAuthentication) {
        User user = userRepository.findByUsername(reqAuthentication.getUsername()).orElseThrow(() ->
                new RuntimeException("User not found"));

        boolean auth = passwordEncoder.matches(reqAuthentication.getPassword(), user.getPassword());

        if (!auth) {
            throw new RuntimeException("Wrong password");
        }

        String token = generateToken(reqAuthentication.getUsername());
        return ResAuthentication.builder()
                .token(token)
                .isSuccess(auth)
                .build();
    }

    public ResIntrospect introspect(ReqIntrospect reqIntrospect) throws ParseException, JOSEException {
        String token = reqIntrospect.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean result = signedJWT.verify(verifier);

        return ResIntrospect.builder()
                .valid(result && expiration.after(new Date()))
                .build();
    }

    private String generateToken(String username) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("ct9")
                .issueTime( new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("username", username)
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Can't create token", e);
            throw new RuntimeException(e);
        }
    }

}
