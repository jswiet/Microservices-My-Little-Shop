package com.project.mylittleshop.entity;

import com.project.mylittleshop.userRole.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "shopUser")
public class User {
    
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    
    @NotBlank
    @Email
    @Column(
            name = "email",
            nullable = false,
            unique = true)
    private String email;
    
    @NotBlank
    @Column(
            name = "firstName",
            nullable = false)
    private String firstName;
    
    @NotBlank
    @Column(
            name = "lastName",
            nullable = false)
    private String lastName;
    
    @NotBlank
    @Column(name = "passwordHashed")
    private String passwordHashed;
    
    @Embedded
    private Address address;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    
    public User(String email, String firstName, String lastName, String passwordHashed, Address address, UserRole userRole) {
        
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordHashed = passwordHashed;
        this.address = address;
        this.userRole = userRole;
    }
    public User() {
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPasswordHashed() {
        return passwordHashed;
    }
    public void setPasswordHashed(String passwordHashed) {
        this.passwordHashed = passwordHashed;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public UserRole getUserRole() {
        return userRole;
    }
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email,
                user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName,
                user.lastName) && Objects.equals(passwordHashed, user.passwordHashed) && Objects.equals(
                address, user.address) && Objects.equals(createdAt, user.createdAt) && userRole == user.userRole;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName, passwordHashed, address, createdAt, userRole);
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", passwordHashed='" + passwordHashed + '\'' +
                ", address=" + address +
                ", createdAt=" + createdAt +
                ", userRole=" + userRole +
                '}';
    }
}
