package com.example.weblibrary.Repository;

import com.example.weblibrary.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    Role findByRoleName(String roleName);
}
