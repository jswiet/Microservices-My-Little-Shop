package com.project.mylittleshop.service;

import com.project.mylittleshop.DTO.AddressDTO;
import com.project.mylittleshop.DTO.DTOMappers.UserDTOMapper;
import com.project.mylittleshop.DTO.NewUserRequestDTO;
import com.project.mylittleshop.DTO.PasswordDTO;
import com.project.mylittleshop.DTO.UserDTO;
import com.project.mylittleshop.entity.User;
import com.project.mylittleshop.exception.ResourceNotFound;
import com.project.mylittleshop.repository.UserRepository;
import com.project.mylittleshop.userRole.UserRole;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    
    public UserService(UserRepository userRepository, UserDTOMapper userDTOMapper) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
    }
    
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id).map(userDTOMapper)
                             .orElseThrow(() -> new ResourceNotFound("User with id: " + id + " not found"));
    }
    
    public UserDTO registerUser(@Valid NewUserRequestDTO newUserRequestDTO) {
        
        User user = new User(newUserRequestDTO.email(),
                newUserRequestDTO.firstName(),
                newUserRequestDTO.lastName(),
                newUserRequestDTO.password(),
                newUserRequestDTO.address(),
                UserRole.USER);
        User newUser = userRepository.save(user);
        return userDTOMapper.apply(newUser);
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
    
    public int updateUserAddress(Long userId, AddressDTO addressDTO) {
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
}
