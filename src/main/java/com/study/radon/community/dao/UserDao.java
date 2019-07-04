package com.study.radon.community.dao;

import org.apache.ibatis.annotations.Mapper;

import com.study.radon.community.model.User;

public interface UserDao {
    
    public int insert(User user);
    
}
