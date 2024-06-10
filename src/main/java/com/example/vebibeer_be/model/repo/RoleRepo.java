package com.example.vebibeer_be.model.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.vebibeer_be.model.entities.Role;


@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
    @Query(value = "SELECT * FROM role WHERE role_name = :roleName", nativeQuery = true)
    Role findByRoleName(@Param("roleName") String roleName);
}
