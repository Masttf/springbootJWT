package cn.edu.hzcu.ljl.work7_2.impl;

import cn.edu.hzcu.ljl.work7_2.entity.Permission;
import cn.edu.hzcu.ljl.work7_2.entity.Role;
import cn.edu.hzcu.ljl.work7_2.entity.User;
import cn.edu.hzcu.ljl.work7_2.repository.PermissionRepository;
import cn.edu.hzcu.ljl.work7_2.repository.RoleRepository;
import cn.edu.hzcu.ljl.work7_2.repository.UserRepository;
import cn.edu.hzcu.ljl.work7_2.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Override
    @Transactional
    public Role add(String name){
        if(roleRepository.findByName(name) != null){
            throw new RuntimeException("role already exists");
        }
        Role role = new Role();
        role.setName(name);
        roleRepository.save(role);
        return roleRepository.findByName(name);
    }
    @Override
    @Transactional
    public void delete(String name){
        Role role = roleRepository.findByName(name);
        if(role == null){
            throw new RuntimeException("role not found");
        }else{
            for(User user : role.getUsers()){
                userRepository.deleteRole(user.getId(), role.getId());
            }
            roleRepository.delete(role);
        }
    }
    @Override
    public Role findByName(String name){
        return roleRepository.findByName(name);
    }

    @Override
    @Transactional
    public void addPermission(String RoleName, String PermissionName){
        Role role = roleRepository.findByName(RoleName);
        if(role == null){
            throw new RuntimeException("role not found");
        }
        Permission permission = permissionRepository.findByName(PermissionName);
        if(permission == null){
            throw new RuntimeException("permission not found");
        }
        roleRepository.addPermission(role.getId(), permission.getId());
    }

    @Override
    @Transactional
    public void deletePermission(String RoleName, String PermissionName){
        Role role = roleRepository.findByName(RoleName);
        if(role == null){
            throw new RuntimeException("role not found");
        }
        Permission permission = permissionRepository.findByName(PermissionName);
        if(permission == null){
            throw new RuntimeException("permission not found");
        }
        roleRepository.deletePermission(role.getId(), permission.getId());
    }
}
