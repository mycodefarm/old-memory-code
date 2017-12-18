package com.jimo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jimo.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

	User findByUsername(String name);
}
