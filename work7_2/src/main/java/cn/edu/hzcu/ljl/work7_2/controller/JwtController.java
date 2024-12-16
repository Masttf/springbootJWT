package cn.edu.hzcu.ljl.work7_2.controller;

import cn.edu.hzcu.ljl.work7_2.config.JwtUtil;
import cn.edu.hzcu.ljl.work7_2.result.ExceptionMsg;
import cn.edu.hzcu.ljl.work7_2.result.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping (value = "/login", method = RequestMethod.POST)
    public ResponseData createAuthenticationToken(@RequestParam String username,
                                                  @RequestParam String password){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        System.out.println("Authentication successful for user: " + username);
        if(Objects.isNull(authentication)){
            return new ResponseData(ExceptionMsg.FAILED, "username or password error");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtil.generateToken(userDetails.getUsername());

        //返回给前端
        Map map = new HashMap();
        map.put("token",jwtToken);
        //postman token填值后即可登入
        //redis

        return new ResponseData(ExceptionMsg.SUCCESS,map);
    }
}

