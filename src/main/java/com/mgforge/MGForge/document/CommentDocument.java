package com.mgforge.MGForge.document;

import com.mgforge.MGForge.enums.CommentTargetType;
import com.mgforge.MGForge.enums.CommentVisibility;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments")
public class CommentDocument extends BaseMongoAuditDocument{

    @Id
    private String id;

    private String tenantId;
    private CommentTargetType targetType;
    private String targetId;
    private String authorId;
    private String message;
    private CommentVisibility visibility;

    public CommentDocument() {
    }

    public CommentDocument(String id, String tenantId, CommentTargetType targetType, String targetId, String authorId, String message, CommentVisibility visibility) {
        this.id = id;
        this.tenantId = tenantId;
        this.targetType = targetType;
        this.targetId = targetId;
        this.authorId = authorId;
        this.message = message;
        this.visibility = visibility;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public CommentTargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(CommentTargetType targetType) {
        this.targetType = targetType;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CommentVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(CommentVisibility visibility) {
        this.visibility = visibility;
    }
}
