package com.qetch.springmvc.service;

import java.util.List;

import com.qetch.springmvc.domain.User;

public interface UserService {
	
	int saveUser(User user);
	
	User getUserById(Long id);
	
	List<User> getAllUsers();
	
	int updateUser(User user);
	
	int deleteUser(User user);
}
