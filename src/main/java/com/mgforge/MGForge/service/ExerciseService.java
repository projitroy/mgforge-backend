package com.mgforge.MGForge.service;

import com.mgforge.MGForge.dto.CreateExerciseInput;
import com.mgforge.MGForge.dto.CreateExerciseMediaInput;
import com.mgforge.MGForge.entity.ExerciseEntity;
import com.mgforge.MGForge.entity.ExerciseMediaEntity;
import com.mgforge.MGForge.enums.MediaType;
import com.mgforge.MGForge.repository.ExerciseMediaRepository;
import com.mgforge.MGForge.repository.ExerciseRepository;
import com.mgforge.MGForge.security.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMediaRepository exerciseMediaRepository;

    public ExerciseService(ExerciseRepository exerciseRepository, ExerciseMediaRepository exerciseMediaRepository) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseMediaRepository = exerciseMediaRepository;
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN','COACH','SUPERADMIN')")
    public List<ExerciseEntity> tenantExercises(){
        UUID tenantId = SecurityUtils.currentPrincipal().getTenantId();
        return exerciseRepository.findAllByTenantId(tenantId);
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN','COACH','SUPERADMIN')")
    public ExerciseEntity createExercise(CreateExerciseInput input){
        UUID tenantId = SecurityUtils.currentPrincipal().getTenantId();

        ExerciseEntity exercise = new ExerciseEntity();
        exercise.setTenantId(tenantId);
        exercise.setName(input.getName());
        exercise.setDescription(input.getDescription());
        exercise.setPrimaryMuscle(input.getPrimaryMuscle());
        exercise.setEquipment(input.getEquipment());
        exercise.setDifficulty(input.getDifficulty());
        exercise.setIsActive("true");

        ExerciseEntity saved = exerciseRepository.save(exercise);

        if(input.getMedia() != null){
            for(CreateExerciseMediaInput media: input.getMedia()){
                ExerciseMediaEntity em = new ExerciseMediaEntity();
                em.setTenantId(tenantId);
                em.setExerciseId(saved.getId());
                em.setMediaType(MediaType.valueOf(media.getMediaType()));
                em.setThumbnailUrl(media.getThumbnailUrl());
                em.setDurationSeconds(media.getDurationSeconds());
                em.setProvider(media.getProvider());
                em.setSortOrder(media.getSortOrder() == null ? 0 : media.getSortOrder());
                exerciseMediaRepository.save(em);
            }
        }

        return saved;
    }

    @PreAuthorize("hasAnyRole('TENANT_ADMIN','ADMIN','COACH','SUPERADMIN')")
    public List<ExerciseMediaEntity> exerciseMedia(UUID exerciseId){
        UUID tenantId = SecurityUtils.currentPrincipal().getTenantId();

        return exerciseMediaRepository.findAllByTenantIdAndExerciseIdOrderBySortOrderAsc(tenantId,exerciseId);
    }
}
