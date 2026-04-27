package com.project.mylittleshop.service;

import com.project.mylittleshop.DTO.MailDTO;
import com.project.mylittleshop.DTO.NewUserRequestDTO;
import com.project.mylittleshop.entity.ConfirmationToken;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistrationService {
    
    private final UserService userService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final MailProducer mailProducer;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public RegistrationService(UserService userService, EmailValidator emailValidator, ConfirmationTokenService confirmationTokenService, MailProducer mailProducer, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.emailValidator = emailValidator;
        this.confirmationTokenService = confirmationTokenService;
        this.mailProducer = mailProducer;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    public String register(@Valid NewUserRequestDTO newUserRequestDTO) {
        boolean isEmailValid = emailValidator.test(newUserRequestDTO.email());
        if (!isEmailValid) {
            throw new IllegalArgumentException("Invalid email");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(newUserRequestDTO.password());
        
        String token = userService.generateTokenForUser(new NewUserRequestDTO(
                newUserRequestDTO.firstName(),
                newUserRequestDTO.lastName(),
                newUserRequestDTO.email(),
                null,
                encodedPassword,
                false,
                false
        ));
        String link = "http://localhost:8081/api/v1/registration/confirmation?token=" + token;
        MailDTO mailDTO = new MailDTO(newUserRequestDTO.email(), newUserRequestDTO.firstName(), link);
        mailProducer.publish(mailDTO);
        
        return token;
    }
    
    @Transactional
    public void confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("Token not found"));
        
        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already confirmed");
        }
        
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException(
                    "Token expired. Try again- token is valid only for 15min!");
        }
        
        confirmationTokenService.setConfirmedAt(token);
        userService.enableUser(
                confirmationToken.getUser().getEmail());
    }
}
