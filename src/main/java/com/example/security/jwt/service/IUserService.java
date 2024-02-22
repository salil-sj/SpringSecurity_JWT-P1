package com.example.security.jwt.service;

import com.example.security.jwt.entity.User;

import java.util.Optional;

public interface IUserService
{
    public User findByUserId(Integer id);

    Optional<User> findByUserName(String userName);
    public Integer saveUser(User user);



}
