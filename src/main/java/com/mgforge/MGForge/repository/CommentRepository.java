package com.mgforge.MGForge.repository;

import com.mgforge.MGForge.document.CommentDocument;
import com.mgforge.MGForge.enums.CommentTargetType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<CommentDocument, String> {
    List<CommentDocument> findAllByTenantIdAndTargetTypeAndTargetIdOrderByCreatedAtDesc(
            String tenantId,
            CommentTargetType targetType,
            String targetId
    );
}
