package com.mgforge.MGForge.repository;

import com.mgforge.MGForge.document.WorkoutLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WorkoutLogRepository extends MongoRepository<WorkoutLogDocument, String> {

    List<WorkoutLogDocument> findAllByTenantIdAndClientIdOrderByCompletedAtDesc(String tenantId, String clientId);

    List<WorkoutLogDocument> findAllByTenantIdAndPlanInstanceIdOrderByCompletedAtDesc(String tenantId, String planInstanceId);
}
