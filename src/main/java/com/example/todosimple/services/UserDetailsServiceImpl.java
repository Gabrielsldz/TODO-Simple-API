package com.example.todosimple.services;


import com.example.todosimple.models.User;
import com.example.todosimple.repositories.UserRepository;
import com.example.todosimple.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);
        if(Objects.isNull(user)){
            throw new UsernameNotFoundException("User not found");
        }
        return new UserSpringSecurity(user.getId(), user.getProfiles(), user.getPassword(), user.getUsername());
    }
}
