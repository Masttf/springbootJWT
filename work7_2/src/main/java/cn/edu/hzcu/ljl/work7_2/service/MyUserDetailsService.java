package cn.edu.hzcu.ljl.work7_2.service;

import cn.edu.hzcu.ljl.work7_2.entity.LoginUser;
import cn.edu.hzcu.ljl.work7_2.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 用户加载逻辑
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }else{
            LoginUser loginUser = new LoginUser();
            loginUser.setUsername(user.getUsername());
            loginUser.setPassword(user.getPassword());
            loginUser.setPermissions(userService.getUserPermissions(user.getId()));
            return loginUser;
        }

    }
}
