package cn.edu.hzcu.ljl.work7_2.repository;

import cn.edu.hzcu.ljl.work7_2.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PermissionRepository extends JpaRepository<Permission,Long>, JpaSpecificationExecutor<Permission> {
    Permission findByName(String permissionName);

}
