package com.example.weblibrary.security;

import com.example.weblibrary.entity.Role;
import com.example.weblibrary.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;




public class UserDetail implements UserDetails {
	private final String name;
	private final String password;
	private final List<GrantedAuthority> authorities;

	public UserDetail(User user) {
		this.name = user.getUsername();
		this.password = user.getPassword();
		Set<Role> roles = user.getRoles();
		List<GrantedAuthority> authorities = new ArrayList<>();
			for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return name;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
