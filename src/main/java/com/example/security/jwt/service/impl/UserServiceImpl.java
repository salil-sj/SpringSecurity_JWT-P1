package com.example.security.jwt.service.impl;

import com.example.security.jwt.entity.User;
import com.example.security.jwt.repository.UserRepository;
import com.example.security.jwt.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

// UserDetails service is a interface provided by Spring security
@Service
public class UserServiceImpl implements IUserService , UserDetailsService
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User findByUserId(Integer id) {

        Optional<User> result =  userRepository.findById(id);
        return result.orElse(null);
    }


    @Override
    public Integer saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user).getId();
    }


    @Override
    public Optional<User> findByUserName(String userName) {
       return userRepository.findByUserName(userName);
    }

    // return a spring security user
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       Optional<User> opt = findByUserName(username);
       User user = opt.get();

       // Created a Spring security user:
        return new
                org.springframework.security.core.userdetails.User(
                        username,
                user.getPassword(),
                user.getRoles().stream().
                        map(role-> new SimpleGrantedAuthority(role))
                        .collect(Collectors.toList())
        );
    }
}
