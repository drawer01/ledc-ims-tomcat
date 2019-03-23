package com.ledc.ims.ServiceImpl;

import com.ledc.ims.Entity.User;
import com.ledc.ims.Repository.UserRepository;
import com.ledc.ims.Utils.JwtUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    UserRepository userRepository;

    //private void setUserRepository (UserRepository userRepository){
        //this.userRepository = userRepository;
   // }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user =userRepository.findByUsername(s);
        return new JwtUser(user);
    }
}
