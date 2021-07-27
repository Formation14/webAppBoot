package webAppBoot.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webAppBoot.models.User;
import webAppBoot.repository.UserRepository;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

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
    public void updateUser(Long id, User user) {
        User u = getUserById(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        u.setEmail(u.getEmail());
        u.setPassword(passwordEncoder.encode(user.getPassword()));
        u.setRoles(user.getRoles());
        userRepository.save(u);
    }


    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.get();
    }

    @Override
    public User getUserByName(Principal principal) {
        return userRepository.findByName(principal.getName());
    }

    @Override
    public void creatDefaultUser() {
        roleService.setRolesDefault();
        User admin = new User();
        admin.setAge(26);
        admin.setEmail("paveltis@tut.by");
        admin.setName("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.getRoles().add(roleService.getAdminRole());
        userRepository.save(admin);

    }

    @Override
    public void chooseRole(User user, String[] chooseRole) {
        for (String role : chooseRole) {
            if (role.contains("ROLE_USER")) {
                user.getRoles().add(roleService.getUserRole());
            } else if (role.contains("ROLE_ADMIN")) {
                user.getRoles().add(roleService.getAdminRole());
            }
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByName(s);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + s);
        }
        return User.fromUser(user);
    }
}