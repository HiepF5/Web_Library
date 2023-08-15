package com.example.weblibrary.Repository;

import com.example.weblibrary.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("select u from User u where u.username = ?1")
	User findByUserName(String username);
	Optional<User> findByUsername(String username);
	User findByEmail(String email);
}
