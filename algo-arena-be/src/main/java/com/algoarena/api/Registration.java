package com.algoarena.api;

import com.algoarena.dao.repo.UserRepo;
import com.algoarena.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class Registration {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    JwtEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Map<String, String> creds) {
        if(userRepo.findById(creds.get("username")).isPresent()){
            return ResponseEntity.badRequest().body(Collections.singletonMap("message","Username already exists"));
        }
        registrationService.registerUser(creds.get("username"), creds.get("password"), "USER");

        // Step 2: Generate JWT token based on the registered user's information
        Instant now = Instant.now();
        long expiry = 36000L; // 10 hours
        String username = creds.get("username");

        // Here you would normally retrieve roles/authorities from the user data
        // For simplicity, we'll assume the user has the role "USER"
        String scope = "ROLE_USER";

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(username)
                .claim("scope", scope)
                .build();

        return ResponseEntity.ok(Collections.singletonMap("token",this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue()));
    }

}
