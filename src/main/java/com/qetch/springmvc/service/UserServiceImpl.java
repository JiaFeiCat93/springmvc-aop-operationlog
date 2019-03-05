package com.qetch.springmvc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qetch.springmvc.domain.User;

@Service
public class UserServiceImpl implements UserService {
	
		
	@Override
	public int saveUser(User user) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public User getUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateUser(User user) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int deleteUser(User user) {
		// TODO Auto-generated method stub
		return 1;
	}

}
