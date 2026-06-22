package com.mgforge.MGForge.repository;

import com.mgforge.MGForge.document.UserPreferencesDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserPreferencesRepository extends MongoRepository<UserPreferencesDocument, String> {
    Optional<UserPreferencesDocument> findByTenantIdAndUserId(String tenantId, String userId);
}
