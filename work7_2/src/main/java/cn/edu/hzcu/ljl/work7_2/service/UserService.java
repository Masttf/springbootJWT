package cn.edu.hzcu.ljl.work7_2.service;
import cn.edu.hzcu.ljl.work7_2.entity.User;

import java.util.List;
public interface UserService {
    User add(String name, String password);

    void delete(String name);

    void addRole(String username, String roleName);

    void deleteRole(String username, String roleName);

    public User findByUsername(String username);
    public List<String> getUserPermissions(Long userId);
}
