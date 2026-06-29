package com.jobportal.service;

import com.jobportal.entity.User;
import com.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean validateLogin(String email, String password) {
    Optional<User> user = userRepository.findByEmail(email);
    if (user.isPresent()) {
        return user.get().getPassword().equals(password);
    }
    return false;
}
}

