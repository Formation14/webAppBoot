package webAppBoot.service;


import webAppBoot.models.User;
import java.util.List;

public interface UserService{

    void addUser(User user);

    List<User> getAllUsers();

    void deleteUser(Long id);

    User getUserById(Long id);

    User getUserByName(String username);

    void creatDefaultUser();
}