package com.mgforge.MGForge.service;

import com.mgforge.MGForge.document.CommentDocument;
import com.mgforge.MGForge.dto.CreateCommentInput;
import com.mgforge.MGForge.enums.CommentTargetType;
import com.mgforge.MGForge.enums.CommentVisibility;
import com.mgforge.MGForge.repository.CommentRepository;
import com.mgforge.MGForge.security.AppPrincipal;
import com.mgforge.MGForge.security.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @PreAuthorize("hasAnyRole('COACH','TENANT_ADMIN','ADMIN','SUPERADMIN')")
    public CommentDocument createComment(CreateCommentInput input){
        AppPrincipal principal = SecurityUtils.currentPrincipal();

        CommentDocument document = new CommentDocument();
        document.setTenantId(principal.getTenantId().toString());
        document.setAuthorId(principal.getUserId().toString());
        document.setTargetType(CommentTargetType.valueOf(input.getTargetType()));
        document.setTargetId(input.getTargetId());
        document.setMessage(input.getMessage());
        document.setVisibility(CommentVisibility.valueOf(input.getVisibility()));

        return commentRepository.save(document);
    }

    @PreAuthorize("isAuthenticated()")
    public List<CommentDocument> comments(String targetType, String targetId) {
        AppPrincipal principal = SecurityUtils.currentPrincipal();

        return commentRepository.findAllByTenantIdAndTargetTypeAndTargetIdOrderByCreatedAtDesc(
                principal.getTenantId().toString(),
                CommentTargetType.valueOf(targetType),
                targetId
        );
    }
}
