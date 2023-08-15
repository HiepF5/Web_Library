package com.example.weblibrary.service.impl;

import com.example.weblibrary.Repository.RoleRepository;
import com.example.weblibrary.Repository.UserRepository;
import com.example.weblibrary.entity.Role;
import com.example.weblibrary.entity.User;
import com.example.weblibrary.entity.dto.SignUpRequest;
import com.example.weblibrary.exception.ObjectExistedException;
import com.example.weblibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public User findByUserName(String UserName) {
		// TODO Auto-generated method stub
		return userRepository.findByUserName(UserName);
	}

	@Override
	public Page<User> findAll(int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size);
		return userRepository.findAll(pageable);
	}

	@Override
	public User create(SignUpRequest signup) throws ObjectExistedException {
		// TODO Auto-generated method stub
		if (!ObjectUtils.isEmpty(userRepository.findByUsername(signup.getUsername()))) {
			throw new ObjectExistedException("Username have existed");
		}
		if (!ObjectUtils.isEmpty(userRepository.findByEmail(signup.getEmail()))) {
			throw new ObjectExistedException("Email have existed");
		}
		Set<String> roleSet = signup.getRoles();
		Set<Role> roles = new HashSet<>();
		if (ObjectUtils.isEmpty(roleSet)) {
			roles.add(roleRepository.findByRoleName("ROLE_USER"));
		}

		if (signup.getUsername().trim().isEmpty()) {
			throw new ObjectExistedException("Username Can Not Be Null");
		}
		if (signup.getUsername().length() < 5) {
			throw new ObjectExistedException("Username Length Exceeds 5 Characters");
		}
		if (signup.getPassword().length() < 5) {
			throw new ObjectExistedException("Password Length Must Exceeds 5 Characters");
		}
		if (signup.getPassword().trim().isEmpty()) {
			throw new ObjectExistedException("Password Can Not Be Null");
		}
		if (signup.getFirstName().trim().isEmpty()) {
			throw new ObjectExistedException("First Name Can Not Be Null");
		}
		if (signup.getLastName().trim().isEmpty()) {
			throw new ObjectExistedException("Last Name Can Not Be Null");
		}
		User user = new User();
		user.setUsername(signup.getUsername());
		user.setPassword(passwordEncoder.encode(signup.getPassword()));
		user.setEmail(signup.getEmail());
		user.setFirstName(signup.getFirstName());
		user.setLastName(signup.getLastName());
		user.setRoles(roles);
		return userRepository.save(user);
	}

}
