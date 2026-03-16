package com.project.mylittleshop.controller;

import com.project.mylittleshop.DTO.AddressDTO;
import com.project.mylittleshop.DTO.NewUserRequestDTO;
import com.project.mylittleshop.DTO.PasswordDTO;
import com.project.mylittleshop.DTO.UserDTO;
import com.project.mylittleshop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("register")
    public UserDTO registerUser(@Valid @RequestBody NewUserRequestDTO newUserRequestDTO) {
        return userService.registerUser(newUserRequestDTO);
    }
    
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @GetMapping("{id}")
    public UserDTO getUserById(@PathVariable("id") Long userId) {
        return userService.getUserById(userId);
    }
    
    @DeleteMapping("delete/{id}")
    public void deleteUserById(@PathVariable("id") Long userId) {
        userService.deleteUserById(userId);
    }
    
    @PatchMapping("{id}/address")
    public int updateUserAddress(@PathVariable("id") Long userId, @RequestBody AddressDTO addressDTO) {
        return userService.updateUserAddress(userId, addressDTO);
    }
    
    @PatchMapping("{id}/changePassword")
    public int changePassword(@PathVariable("id") Long userId, @RequestBody PasswordDTO passwordDTO) {
        return userService.changePassword(userId, passwordDTO);
    }
    
}
