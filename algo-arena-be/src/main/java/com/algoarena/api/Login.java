package com.algoarena.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class Login {

    @Autowired
    JwtEncoder encoder;

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:5173") // For testing in DEV env
    public ResponseEntity<Map<String,String>> token() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Instant now = Instant.now();
        long expiry = 360000L; // 10 hours

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        return ResponseEntity.ok(Collections.singletonMap("token", this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue()));
    }

}
