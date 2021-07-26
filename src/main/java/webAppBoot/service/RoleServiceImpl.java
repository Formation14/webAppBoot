package webAppBoot.service;

import org.springframework.stereotype.Service;
import webAppBoot.models.Role;
import webAppBoot.repository.RoleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private static final String ROLE_USER ="ROLE_USER";
    private static final String ROLE_ADMIN ="ROLE_ADMIN";

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Set<Role> getRoleSet(Set<String> roles) {
        return new HashSet<>(getAllRoles());
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
        return getAllRoles().stream().filter(role -> role.getName().equals(name)).findFirst().orElse(null);
    }

    public void setAdminRoleDefault() {

        Role adminRole = new Role();
        adminRole.setName(ROLE_ADMIN);
        roleRepository.save(adminRole);
    }

    public void setUserRoleDefault() {
        Role userRole = new Role();
        userRole.setName(ROLE_USER);
        roleRepository.save(userRole);
    }

    @Override
    public void setRolesDefault() {
        setAdminRoleDefault();
        setUserRoleDefault();
    }

}