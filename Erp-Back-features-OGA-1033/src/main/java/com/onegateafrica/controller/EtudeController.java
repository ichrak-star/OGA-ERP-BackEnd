package com.onegateafrica.controller;

import com.onegateafrica.Entity.AppelDoffreEntity;
import com.onegateafrica.Entity.EtudeEntity;
import com.onegateafrica.Entity.ProjectEntity;
import com.onegateafrica.Entity.UserEntity;
import com.onegateafrica.dto.*;
import com.onegateafrica.services.EtudeService;
import com.onegateafrica.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@RestController
@RequestMapping("api")
public class EtudeController {

    private EtudeService etudeService;
    private ImageService imageService;

    @Autowired
    public EtudeController(EtudeService etudeService, ImageService imageService) {
        this.etudeService = etudeService;
        this.imageService = imageService;
    }




    @GetMapping("etudes")
    public List<EtudeEntity> getAllEtudes(){
        return etudeService.getAllEtudes();
    }

    @GetMapping("etude/{id}")
    public ResponseEntity<EtudeDto> getProjectByIdForResponse(@PathVariable(name = "id")long id){

        EtudeEntity etude = etudeService.getEtudeById(id);
        UserEntity user = etude.getUserEntity();
        AppelDoffreEntity appelDoffreEntity = etude.getAppelDoffre();
        AppelDoffreDto appelDoffreDto = new AppelDoffreDto(appelDoffreEntity.getId(),
                appelDoffreEntity.getReference());
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

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/downloadFile/")
                .path(etude.getFileName())
                .toUriString();
        EtudeDto etudeDto = new EtudeDto(etude.getId(),
                etude.getReferenceProjet(),
                etude.getDatepost(),

                etude.getTitre(),
                etude.getDescription(),
                etude.getStatus(),
                etude.getFileName(),
                fileDownloadUri,
                userDto,
                appelDoffreDto);
        Optional<EtudeEntity> etude1 = etudeService.getEtudeByIdForResponse(id);
        if (etude1.isPresent()){

            return new ResponseEntity(etudeDto,HttpStatus.OK);
        }else{
            return new ResponseEntity("etude introuvable",HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping("user/{id}/etude")
//    public ResponseEntity<EtudeEntity> saveEtude(@RequestBody EtudeEntity etude, @PathVariable(name = "id") long id){
//        return etudeService.creerEtude(etude,id);
//    }
    @RequestMapping(value = "/user/{id}/appelDoffre/{appelDoffreId}/etude", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public ResponseEntity<EtudeCreationDto> addUser( @ModelAttribute EtudeEntity etude,@PathVariable(name = "id") long id,@PathVariable(name = "appelDoffreId") long appelDoffreId,MultipartFile file){

//        if (userService.getUserByUserName(user.getUserName())!=null){
//            return new ResponseEntity(new UserCreationDto(user,"null","this "),HttpStatus.BAD_REQUEST);
//        }
        String fileName = imageService.storeFile(file);

        etude.setFileName(fileName);

        return new ResponseEntity(new EtudeCreationDto(etudeService.creerEtude(etude,id,appelDoffreId).getBody(), ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/downloadFile/")
                .path(fileName)
                .toUriString(),"project succsusfully added to database"),HttpStatus.CREATED);
    }

    @PutMapping("etude/{id}")
    public ResponseEntity<EtudeEntity> updateEtude(@PathVariable(value = "id") long id , @RequestBody EtudeEntity newEtude){
        if (etudeService.getEtudeById(id)!=null){
            return etudeService.updateEtude(id,newEtude);
        }else{
            return new ResponseEntity("Etude does not exist", HttpStatus.NOT_FOUND);
        }
    }


}
