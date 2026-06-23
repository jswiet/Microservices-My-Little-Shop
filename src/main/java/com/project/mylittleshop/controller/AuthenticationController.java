package com.project.mylittleshop.controller;

import com.project.mylittleshop.DTO.AuthenticationRequest;
import com.project.mylittleshop.entity.User;
import com.project.mylittleshop.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Value("${jwt.expiration}")
    private long jwtExpiration;
    public AuthenticationController(AuthenticationManager authenticationManager, JwtService jwtService, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }
    
    @PostMapping("")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.email(),
                    authenticationRequest.password()));
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        
        final User userDetails = (User) userDetailsService.loadUserByUsername(authenticationRequest.email());
        
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", userDetails.getId());
        final String jwt = jwtService.generateToken(extraClaims, userDetails);
        ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
                                              .httpOnly(true)
                                              .secure(false)
                                              .path("/")
                                              .maxAge(jwtExpiration / 1000)
                                              .sameSite("Strict")
                                              .build();
        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, cookie.toString())
                             .body("Login successful");
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                                              .httpOnly(true)
                                              .secure(false)
                                              .path("/")
                                              .maxAge(0)
                                              .sameSite("Strict")
                                              .build();
        
        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, cookie.toString())
                             .body("Logged out");
    }
    
}
