package webAppBoot.service;


import webAppBoot.models.User;

import java.util.List;

public interface UserService{
    void addUser(User user);

    List<User> getAllUsers();

    void deleteUser(Long id);

    void updateUser(String[] role, User user);

    User getUserById(Long id);

    void creatDefaultUser();

    User chooseRole(User user, String[] chooseRole);

    User findByUserName(String userName);
}