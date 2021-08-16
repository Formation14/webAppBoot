package webAppBoot.service;

import org.springframework.stereotype.Service;
import webAppBoot.models.Role;
import webAppBoot.repository.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getUserRole() {
        return getRoleByName(ROLE_USER);
    }

    @Override
    public Role getAdminRole() {
        return getRoleByName(ROLE_ADMIN);
    }

    public Role getRoleByName(String name) {
        return getAllRoles().stream().filter(role -> role.getRole().equals(name)).findFirst().orElse(null);
    }

    public void setAdminRoleDefault() {

        Role adminRole = new Role();
        adminRole.setRole(ROLE_ADMIN);
        roleRepository.save(adminRole);
    }

    public void setUserRoleDefault() {
        Role userRole = new Role();
        userRole.setRole(ROLE_USER);
        roleRepository.save(userRole);
    }

    @Override
    public void setRolesDefault() {
        setAdminRoleDefault();
        setUserRoleDefault();
    }

}