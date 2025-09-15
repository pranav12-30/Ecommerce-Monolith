package com.app.ecommerce;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    List<User> userList = new ArrayList<>();
    private Long id = 1L;

    public List<User> fetchAllUsers() {
        return userList;
    }

    public void addUser(User user) {
        user.setId(id++);
        userList.add(user);
    }

    public Optional<User> fetchUser(Long id) {
        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public boolean updateUser(Long id, User updatedUser) {
        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(user -> {
                    user.setFirstName(updatedUser.getFirstName());
                    user.setLastName(updatedUser.getLastName());
                    return true;
                }).orElse(false);
    }
}
