package com.mooncowpines.KinoStats.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mooncowpines.KinoStats.Exceptions.ValidationException;
import com.mooncowpines.KinoStats.Model.User;
import com.mooncowpines.KinoStats.Repository.UserRepository;

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

    public void updateUser(User updatedUser){
        User user;
        if (userRepository.findById(updatedUser.getId()).isPresent()){
            user = userRepository.findById(updatedUser.getId()).get();
        }
        else {
            throw new ValidationException("User not found");
        }
        if (userRepository.findByUsername(updatedUser.getUsername()).isPresent()) {
            throw new ValidationException("Username already taken");
        }
        if (userRepository.findByEmail(updatedUser.getEmail()).isPresent()) {
            throw new ValidationException("Email already registered");
        }
        user.setEmail(updatedUser.getEmail());
        user.setUsername(updatedUser.getUsername());
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
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
