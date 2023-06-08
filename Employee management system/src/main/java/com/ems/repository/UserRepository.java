package com.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.ems.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	//method to find user details using userName and password
	User findByUserNameAndPassword(String userName, String password);
}
