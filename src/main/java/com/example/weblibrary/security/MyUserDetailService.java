package com.example.weblibrary.security;

import com.example.weblibrary.Repository.UserRepository;
import com.example.weblibrary.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class MyUserDetailService implements UserDetailsService{
    @Autowired
    private  UserRepository repository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userInfo = repository.findByUsername(username);
        return userInfo.map(UserDetail::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

    }
}
