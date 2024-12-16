package cn.edu.hzcu.ljl.work7_2;

import cn.edu.hzcu.ljl.work7_2.config.JwtUtil;
import cn.edu.hzcu.ljl.work7_2.service.PermissionService;
import cn.edu.hzcu.ljl.work7_2.service.RoleService;
import cn.edu.hzcu.ljl.work7_2.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class Work72ApplicationTests {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;
    @Test
    void contextLoads() {
        String token = jwtUtil.generateToken("admin");
        System.out.println(token);
        System.out.println(jwtUtil.extractUsername(token));
    }
    @Test
    void init(){
        roleService.add("admin");
        roleService.add("user");
        permissionService.add("admin");
        permissionService.add("user");
        userService.add("admin","123456");
        userService.add("user","123456");
        userService.addRole("admin","admin");
        userService.addRole("user","user");
        roleService.addPermission("admin","admin");
        roleService.addPermission("user","user");
    }
}
