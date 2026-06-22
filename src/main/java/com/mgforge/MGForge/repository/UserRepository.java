package com.mgforge.MGForge.repository;

import com.mgforge.MGForge.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query(value = "SELECT * FROM users WHERE mobile = :mobile", nativeQuery = true)
    Optional<UserEntity> findByMobile(@Param("mobile")String mobile);

    List<UserEntity> findAllByTenantId(UUID tenantId);

    boolean existsByIdAndTenantId(UUID id, UUID tenantId);
}
