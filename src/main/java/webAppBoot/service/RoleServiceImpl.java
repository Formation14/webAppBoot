package webAppBoot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webAppBoot.models.Role;
import webAppBoot.repository.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private static final String ROLE_USER ="ROLE_USER";
    private static final String ROLE_ADMIN ="ROLE_ADMIN";

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getDefaultRole() {
        return getRoleByName(ROLE_USER);
    }

    @Override
    public Role getAdminRole() {
        return getRoleByName(ROLE_ADMIN);
    }
    @Override
    public Role getRoleByName(String name) {
        return getAllRoles().stream().filter(role -> role.getName().equals(name)).findFirst().orElse(null);
    }
    @Override
    public void setAdminRoleDefault() {

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleRepository.save(adminRole);
    }
    @Override
    public void setUserRoleDefault() {
        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        roleRepository.save(userRole);
    }

    @Override
    public void setRolesDefault() {
        setAdminRoleDefault();
        setUserRoleDefault();
    }



}