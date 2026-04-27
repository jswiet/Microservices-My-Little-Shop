package com.project.mylittleshop.controller;

import com.project.mylittleshop.DTO.NewUserRequestDTO;
import com.project.mylittleshop.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration/")
public class RegistrationController {
    
    private final RegistrationService registrationService;
    
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }
    
    @PostMapping()
    public String registerUser(@Valid @RequestBody NewUserRequestDTO newUserRequestDTO) {
        return registrationService.register(newUserRequestDTO);
    }
    
    @GetMapping("/confirmation")
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        registrationService.confirmToken(token);
        return ResponseEntity.ok("Email enabled");
    }
}
