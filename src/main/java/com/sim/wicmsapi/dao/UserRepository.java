package com.sim.wicmsapi.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sim.wicmsapi.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsernameAndPassword(String username,String password);
	User findByUsername(String username);
	
}
