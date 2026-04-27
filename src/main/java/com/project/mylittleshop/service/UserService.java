package com.project.mylittleshop.service;

import com.project.mylittleshop.DTO.AddressDTO;
import com.project.mylittleshop.DTO.DTOMappers.UserDTOMapper;
import com.project.mylittleshop.DTO.NewUserRequestDTO;
import com.project.mylittleshop.DTO.PasswordDTO;
import com.project.mylittleshop.DTO.UserDTO;
import com.project.mylittleshop.entity.ConfirmationToken;
import com.project.mylittleshop.entity.User;
import com.project.mylittleshop.exception.ResourceNotFound;
import com.project.mylittleshop.repository.UserRepository;
import com.project.mylittleshop.userRole.UserRole;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private final ConfirmationTokenService confirmationTokenService;
    
    public UserService(UserRepository userRepository, UserDTOMapper userDTOMapper, ConfirmationTokenService confirmationTokenService) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
        
        this.confirmationTokenService = confirmationTokenService;
    }
    
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id).map(userDTOMapper)
                             .orElseThrow(() -> new ResourceNotFound("User with id: " + id + " not found"));
    }
    
    public boolean doesUserExist(@Valid NewUserRequestDTO newUserRequestDTO) {
        return userRepository.findByEmail(newUserRequestDTO.email()).isPresent();
    }
    
    @Transactional
    public String generateTokenForUser(@Valid NewUserRequestDTO newUserRequestDTO) {
        if (doesUserExist(newUserRequestDTO)) {
            throw new IllegalStateException("Email already exists!");
        }
        User user = new User(
                newUserRequestDTO.firstName(),
                newUserRequestDTO.lastName(),
                newUserRequestDTO.email(),
                newUserRequestDTO.password(),
                null,
                UserRole.USER,
                false,
                false
        );
        userRepository.save(user);
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token,
                LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }
    
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                             .stream()
                             .map(userDTOMapper)
                             .collect(Collectors.toList());
    }
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }
    
    public int updateUserAddress(Long userId, @Valid AddressDTO addressDTO) {
        return userRepository.updateAddress(
                userId,
                addressDTO.country(),
                addressDTO.city(),
                addressDTO.street(),
                addressDTO.postCode()
        );
    }
    
    public int changePassword(Long userId, PasswordDTO passwordDTO) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new ResourceNotFound("User not found"));
        if (!passwordDTO.oldPassword().equals(user.getPasswordHashed())) {
            throw new IllegalArgumentException("Old password is wrong");
        }
        String newPassword = passwordDTO.newPassword();
        return userRepository.changePassword(userId, newPassword);
    }
    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                             .orElseThrow(() -> new UsernameNotFoundException(
                                     "Username with email: " + email + " not found"));
    }
    
    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }
}
