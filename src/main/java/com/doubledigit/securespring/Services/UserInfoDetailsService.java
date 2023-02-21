package com.doubledigit.securespring.Services;

import com.doubledigit.securespring.Models.UserInfo;
import com.doubledigit.securespring.Models.UserInfoMapping;
import com.doubledigit.securespring.Repositories.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoDetailsService implements UserDetailsService {

    @Autowired
    UserInfoRepo userInfoRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         Optional<UserInfo> userInfo = userInfoRepo.findByName(username);
        return userInfo.map(UserInfoMapping::new).orElseThrow(()->new UsernameNotFoundException("User not found"));
    }
}
