package com.mgforge.MGForge.repository;

import com.mgforge.MGForge.document.PlanDefinitionDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlanDefinitionRepository extends MongoRepository<PlanDefinitionDocument, String> {
}
