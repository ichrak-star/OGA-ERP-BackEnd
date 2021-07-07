package com.onegateafrica.controller;

import com.onegateafrica.Entity.AppelDoffreEntity;
import com.onegateafrica.Entity.EtudeEntity;
import com.onegateafrica.Entity.QualityEntity;
import com.onegateafrica.Entity.UserEntity;
import com.onegateafrica.dto.*;
import com.onegateafrica.services.ImageService;
import com.onegateafrica.services.QualityService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.Column;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
@NoArgsConstructor

public class QualityController {

    private  QualityService qualityService;
    private  ImageService imageService;

    @Autowired
    public QualityController(QualityService qualityService,ImageService imageService) {
        this.qualityService = qualityService;
        this.imageService = imageService;
    }

    @GetMapping("qualityRapports")
    public List<QualityEntity> getAllQualityRapport(){
        return qualityService.getAllQualityRapport();
    }

    @GetMapping("qualityRapport/{id}")
    public ResponseEntity<Optional<QualityEntity>> getEtudeByIdForResponse(@PathVariable(name = "id")long id){

        QualityEntity qualityEntity = qualityService.getQualityRapport(id);
        UserEntity user = qualityEntity.getUserEntity();
//
        UserDto userDto = new UserDto(user.getId(),
                user.getUserName(),
                user.getNom(),
                user.getPrenom(),
                user.getEmail(),
                user.getImage(),
                user.getProfileImage(),
                user.getTelephone(),
                user.isDisabled(),
                user.getDepartement(),
                user.getSex(),
                user.getRole());
        Optional<QualityEntity> quality = qualityService.getQualityRapportByIdForResponse(id);

//       UserEntity user = etude.get().getUserEntity();
//        AppelDoffreEntity appelDoffreEntity = etude.get().getAppelDoffre();




        if (quality.isPresent()){

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/downloadFile/")
                    .path(quality.get().getFileName())
                    .toUriString();

            return new ResponseEntity(new QualityCreationDto(
                    qualityEntity.getId(),
                    qualityEntity.getProjectReference(),
                    qualityEntity.getDescription(),
                    qualityEntity.getTitle(),
                    qualityEntity.getFileName(),
                    qualityEntity.getProject().getTitle(),
                    userDto,
                    fileDownloadUri
            ),HttpStatus.OK);
        }else{
            return new ResponseEntity("etude introuvable",HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/user/{id}/project/{projectId}/QualityRapport", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public ResponseEntity<QualityCreationDto> addQualityrapport(@ModelAttribute QualityEntity qualityEntity, @PathVariable(name = "id") long id, @PathVariable(name = "projectId") long projectId, MultipartFile file){

//        if (userService.getUserByUserName(user.getUserName())!=null){
//            return new ResponseEntity(new UserCreationDto(user,"null","this "),HttpStatus.BAD_REQUEST);
//        }
        String fileName = imageService.storeFile(file);

        qualityEntity.setFileName(fileName);

        return new ResponseEntity(new QualityPostDto(qualityService.creerQualityRapport(qualityEntity,id,projectId).getBody(), ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/downloadFile/")
                .path(fileName)
                .toUriString()),HttpStatus.CREATED);
    }

    @PutMapping("qualityRapport/{id}")
    public ResponseEntity<QualityEntity> updateQualityRapport(@PathVariable(value = "id") long id , @RequestBody QualityEntity newQuality){
        if (qualityService.getQualityRapport(id)!=null){
            return qualityService.updateQualityRapport(id,newQuality);
        }else{
            return new ResponseEntity("Quality Rapport does not exist", HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(value = "qualityRapport/{id}/updateQualityRapportFile", method = RequestMethod.PUT, consumes = { "multipart/form-data" })
    public QualityEntity updateQualityRapportFile(@PathVariable(value = "id") long id, @ModelAttribute MultipartFile file){

        QualityEntity foundQualityRapport = qualityService.getQualityRapport(id);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/downloadFile/")
                .path(imageService.storeFile(file))
                .toUriString();


        foundQualityRapport.setFileName(fileDownloadUri);
        return qualityService.addQualityRapport(foundQualityRapport);
    }
}