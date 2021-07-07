package webAppBoot.service;


import webAppBoot.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    Role getDefaultRole();
    Role getAdminRole();
    Role getRoleByName(String name);
    void setAdminRoleDefault();
    void setUserRoleDefault();
    void setRolesDefault();
}
