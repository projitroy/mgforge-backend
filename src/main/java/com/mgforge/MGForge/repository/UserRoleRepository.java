package com.mgforge.MGForge.repository;

import com.mgforge.MGForge.entity.UserRoleEntity;
import com.mgforge.MGForge.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleId> {

    @Query("select r.roleId from UserRoleEntity r where r.userId = :userId")
    List<String> findRolesByUserId(@Param("userId")UUID userId);

    void deletedBuyUserId(UUID userId);
}
