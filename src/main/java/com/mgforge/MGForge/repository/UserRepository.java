package com.mgforge.MGForge.repository;

import com.mgforge.MGForge.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByMobile(String mobile);
}
