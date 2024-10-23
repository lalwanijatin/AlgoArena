package com.algoarena.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(String username, String password, String role) {
        String encodedPassword = passwordEncoder.encode(password);
        UserDetails user = User.withUsername(username)
                .password(encodedPassword)
                .roles(role)
                .build();
        userDetailsManager.createUser(user);
    }
}
