package com.mooncowpines.KinoStats.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mooncowpines.KinoStats.DTO.UserDetailsUpdateDTO;
import com.mooncowpines.KinoStats.DTO.UserPasswordUpdateDTO;
import com.mooncowpines.KinoStats.Exceptions.InvalidPasswordException;
import com.mooncowpines.KinoStats.Exceptions.ValidationException;
import com.mooncowpines.KinoStats.Model.User;
import com.mooncowpines.KinoStats.Repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final MovieListService movieListService;

    private final PasswordEncoder passwordEncoder;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public Optional<User> getUserByName(String name){
        return userRepository.findByUsername(name);
    }

    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void addUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ValidationException("Username already taken");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ValidationException("Email already registered");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userRepository.save(user);
        movieListService.createWatchlist(newUser);
    }

    public void updateUser(Long id, UserDetailsUpdateDTO dto) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        Optional<User> byUsername = userRepository.findByUsername(dto.username());
        if (byUsername.isPresent() && !byUsername.get().getId().equals(user.getId())) {
            throw new ValidationException("Username already taken");
        }

        Optional<User> byEmail = userRepository.findByEmail(dto.email());
        if (byEmail.isPresent() && !byEmail.get().getId().equals(user.getId())) {
            throw new ValidationException("Email already registered");
        }

        user.setEmail(dto.email());
        user.setUsername(dto.username());
        userRepository.save(user);
    }

    public void updatePassword(Long id, UserPasswordUpdateDTO dto) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!passwordEncoder.matches(dto.oldPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        user.setPassword(passwordEncoder.encode(dto.newPassword()));
        userRepository.save(user);
    }

    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
        .matcher(emailAddress)
        .matches();
    }
}
