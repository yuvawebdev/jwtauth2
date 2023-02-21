package com.doubledigit.securespring.Controllers;

import com.doubledigit.securespring.Models.AuthRequest;
import com.doubledigit.securespring.Models.UserInfo;
import com.doubledigit.securespring.Repositories.UserInfoRepo;
import com.doubledigit.securespring.Services.JwtService;
import com.doubledigit.securespring.Services.UserLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserLoadService userLoadService;
    @Autowired
    private UserInfoRepo userInfoRepo;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/add")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return userLoadService.addUser(userInfo);
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (authentication.isAuthenticated())
            return jwtService.generateToken(authRequest.getUsername());
        else
            throw  new UsernameNotFoundException("Invalid user request");
    }

}
