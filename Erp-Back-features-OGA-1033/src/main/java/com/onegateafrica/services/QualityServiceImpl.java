package com.onegateafrica.services;

import com.onegateafrica.Entity.*;
import com.onegateafrica.repository.QualityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class QualityServiceImpl implements QualityService{

    private final QualityRepository qualityRepository;
    private final UserService userService;
    private final ProjectService projectService;

    @Autowired
    public QualityServiceImpl(QualityRepository qualityRepository,UserService userService,ProjectService projectService) {
        this.qualityRepository = qualityRepository;
        this.userService = userService;
        this.projectService = projectService;
    }



    @Override
    public List<QualityEntity> getAllQualityRapport() {
        return qualityRepository.findAll();
    }

    @Override
    public ResponseEntity<QualityEntity> creerQualityRapport(QualityEntity qualityEntity, Long id, long projectId) {
        UserEntity user = userService.getUserByID(id);
        ProjectEntity project = projectService.getProjectById(projectId);

        if (user != null || project!= null){
            qualityEntity.setUserEntity(user);
            qualityEntity.setProject(project);
            return new ResponseEntity<QualityEntity>(qualityRepository.save(qualityEntity), HttpStatus.CREATED);
        }else{
            return new ResponseEntity("this user or project dos not exist ",HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public QualityEntity getQualityRapport(Long id) {
        return qualityRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Not found"));
    }

    @Override
    public ResponseEntity<QualityEntity> updateQualityRapport(Long id, QualityEntity newQualityRapport) {
        QualityEntity qualityEntity = getQualityRapport(id);
        if (qualityEntity != null){
            qualityEntity.setTitle(newQualityRapport.getTitle());
            qualityEntity.setDescription(newQualityRapport.getDescription());
            qualityEntity.setProjectReference(newQualityRapport.getProjectReference());


            qualityRepository.save(qualityEntity);

            return new ResponseEntity(qualityEntity,HttpStatus.OK);

        }else{
            return new ResponseEntity("This Rapport Quality does not exist",HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Optional<QualityEntity> getQualityRapportByIdForResponse(long id) {
        return qualityRepository.findById(id);
    }

    @Override
    public QualityEntity addQualityRapport(QualityEntity qualityEntity) {
        return qualityRepository.save(qualityEntity);
    }
}