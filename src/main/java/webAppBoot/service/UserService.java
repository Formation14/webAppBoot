package webAppBoot.service;


import webAppBoot.models.User;
import java.security.Principal;
import java.util.List;

public interface UserService{
    void addUser(User user);

    List<User> getAllUsers();

    void deleteUser(Long id);

    void updateUser(Long id, User updatedUser);

    User getUserById(Long id);

    User getUserByName(String username);

    void creatDefaultUser();
}