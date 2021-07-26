package webAppBoot.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import webAppBoot.models.User;

import java.security.Principal;
import java.util.List;

public interface UserService extends UserDetailsService {
    void addUser(User user);

    List<User> getAllUsers();

    void deleteUser(Long id);

    void updateUser(Long id, User updatedUser);

    User getUserById(Long id);

    User getUserByName(Principal principal);

    void creatDefaultUser();

    User chooseRole(User user, String[] chooseRole);
}