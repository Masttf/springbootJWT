package cn.edu.hzcu.ljl.work7_2.service;

import cn.edu.hzcu.ljl.work7_2.entity.Role;

public interface RoleService {
    Role add(String name);

    void delete(String name);

    Role findByName(String name);

    void addPermission(String RoleName, String PermissionName);

    void deletePermission(String RoleName, String PermissionName);
}
