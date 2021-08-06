package webAppBoot.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webAppBoot.models.User;
import webAppBoot.repository.UserRepository;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(getUserById(id).getId());

    }


    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.get();
    }

    @Override
    public User getUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void creatDefaultUser() {
        roleService.setRolesDefault();
        User admin = new User();
        admin.setAge(26);
        admin.setEmail("paveltis@tut.by");
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.getRoles().add(roleService.getUserRole());
        admin.getRoles().add(roleService.getAdminRole());

        User u = new User();
        u.setAge(15);
        u.setEmail("user@tut.by");
        u.setUsername("user");
        u.setPassword(passwordEncoder.encode("user"));
        u.getRoles().add(roleService.getUserRole());

        userRepository.save(admin);
        userRepository.save(u);

    }
}