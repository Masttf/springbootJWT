package cn.edu.hzcu.ljl.work7_2.impl;

import cn.edu.hzcu.ljl.work7_2.entity.Permission;
import cn.edu.hzcu.ljl.work7_2.entity.Role;
import cn.edu.hzcu.ljl.work7_2.repository.PermissionRepository;
import cn.edu.hzcu.ljl.work7_2.repository.RoleRepository;
import cn.edu.hzcu.ljl.work7_2.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Permission findByName(String name) {
        return permissionRepository.findByName(name);
    }

    @Override
    @Transactional
    public Permission add(String name){
        if(permissionRepository.findByName(name) != null){
            throw new RuntimeException("Permission already exists");
        }
        Permission permission = new Permission();
        permission.setName(name);
        return permissionRepository.save(permission);
    }

    @Override
    @Transactional
    public void delete(String name){
        Permission permission = permissionRepository.findByName(name);
        if(permission != null){
            for(Role role : permission.getRoles()){
                roleRepository.deletePermission(role.getId(), permission.getId());
            }
            permissionRepository.delete(permission);
        }else{
            throw new RuntimeException("Permission not found");
        }
    }
}
