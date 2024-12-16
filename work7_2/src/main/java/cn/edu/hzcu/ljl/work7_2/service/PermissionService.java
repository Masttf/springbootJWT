package cn.edu.hzcu.ljl.work7_2.service;

import cn.edu.hzcu.ljl.work7_2.entity.Permission;

public interface PermissionService {
    public abstract Permission findByName(String name);

    public abstract Permission add(String name);

    public abstract void delete(String name);
}
