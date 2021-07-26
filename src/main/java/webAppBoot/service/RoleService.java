package webAppBoot.service;


import webAppBoot.models.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

    List<Role> getAllRoles();

    Set<Role> getRoleSet(Set<String> roles);

    Role getUserRole();

    Role getAdminRole();

    void setRolesDefault();
}
