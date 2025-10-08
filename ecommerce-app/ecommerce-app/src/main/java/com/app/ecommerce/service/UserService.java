package com.app.ecommerce.service;

import com.app.ecommerce.model.User;
import com.app.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private Long id = 1L;

    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> fetchUser(Long id) {
        return userRepository.findById(id);
    }

    public boolean updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(updatedUser.getFirstName());
                    user.setLastName(updatedUser.getLastName());
                    userRepository.save(user);
                    return true;
                }).orElse(false);
    }
}
