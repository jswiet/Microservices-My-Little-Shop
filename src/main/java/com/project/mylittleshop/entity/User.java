package com.project.mylittleshop.entity;

import com.project.mylittleshop.userRole.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "shopUser")
public class User implements UserDetails {
    
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
    @Email
    @Column(
            name = "email",
            nullable = false,
            unique = true)
    private String email;
    
    @NotBlank
    @Column(name = "passwordHashed",
            nullable = false)
    private String passwordHashed;
    
    @Embedded
    private Address address;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;
    
    private boolean locked = false;
    
    private boolean enabled = false;
    
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    public User(String firstName, String lastName, String email, String passwordHashed, Address address, UserRole userRole, boolean locked, boolean enabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHashed = passwordHashed;
        this.address = address;
        this.userRole = userRole;
        this.locked = locked;
        this.enabled = enabled;
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
    public Boolean getLocked() {
        return locked;
    }
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
    public Boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email,
                user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName,
                user.lastName) && Objects.equals(passwordHashed, user.passwordHashed) && Objects.equals(
                address, user.address) && Objects.equals(createdAt,
                user.createdAt) && userRole == user.userRole && Objects.equals(locked,
                user.locked) && Objects.equals(enabled, user.enabled);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName, passwordHashed, address, createdAt, userRole, locked,
                enabled);
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
                ", locked=" + locked +
                ", enabled=" + enabled +
                '}';
    }
    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
        return List.of(authority);
    }
    @Override
    public @Nullable String getPassword() {
        return passwordHashed;
    }
    @Override
    public @NonNull String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
