package com.onegateafrica.controller;

import com.onegateafrica.Entity.DemandeEntity;
import com.onegateafrica.Entity.ProjectEntity;
import com.onegateafrica.Entity.TacheEntity;
import com.onegateafrica.Entity.UserEntity;
import com.onegateafrica.dto.TacheDto;
import com.onegateafrica.dto.UserDto;
import com.onegateafrica.services.TacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
public class TacheController {

    public TacheService tacheService;

    @Autowired
    public TacheController(TacheService tacheService) {
        this.tacheService = tacheService;
    }

    @GetMapping("taches")
    public List<TacheEntity> getDemandes() {
        return tacheService.getAllTaches();
    }

    @GetMapping("nonAffectedUsersToTache/{id}")
    public List<UserEntity> getAllUsersNonAffected(@PathVariable(value = "id")long id){
        return tacheService.getAllNonAffectedUserToTache(id);
    }

    @GetMapping("tache/{id}")
    public ResponseEntity<TacheDto> getTacheById(@PathVariable(value = "id") long id){
        List<UserDto> userDtos = new ArrayList<>();
        Optional<TacheEntity> tache = tacheService.getTacheByIdForResponse(id);
        TacheEntity tacheEntity = tacheService.getTacheById(id);
        TacheDto tacheDto = new TacheDto();
        for (Iterator<UserEntity> i = tacheEntity.getUsers().iterator(); i.hasNext();){
            UserEntity user = i.next();
            userDtos.add(new UserDto(user.getId(),
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
                    user.getRole()

            ));
        }
        tacheDto.setId(tache.get().getId());
        tacheDto.setTitre(tache.get().getTitre());
        tacheDto.setDescription(tache.get().getDescription());
        tacheDto.setProgress(tache.get().isProgress());
        tacheDto.setUsers(userDtos);
        if (tache.isPresent()){
            return new ResponseEntity(tacheDto, HttpStatus.OK);
        }else {
            return new ResponseEntity("Cette tache n'existe pas",HttpStatus.NOT_FOUND);
        }

    }
    @PostMapping("tache/{id}/users")
    public TacheEntity addTacheToUsers(@PathVariable(value = "id")long id, @RequestBody ArrayList<Long> users){
        return tacheService.addUsersToTache(id,users);
    }

    @PostMapping("projet/{id}/tache")
    public ResponseEntity<TacheEntity> addTache(@PathVariable(value = "id") long id,@RequestBody TacheEntity tache){
        if (tache != null){
            return tacheService.addTache(id,tache);
        }else{
            return new ResponseEntity("vous devez remplir tous les champs",HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("tache/{id}")
    public ResponseEntity<TacheEntity> updateTache(@PathVariable(value = "id")long id,TacheEntity newTache){
        if (newTache != null){
            return new ResponseEntity(tacheService.updateTache(id,newTache),HttpStatus.OK);
        }else{
            return new ResponseEntity("Vous devez remplir tous les champs ",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("tache/{id}")
    public ResponseEntity deleteTache(@PathVariable(value = "id")long id){
        boolean result = tacheService.deleteTache(id);

        if(result == true){
            return new ResponseEntity("Cette tache a été suppripmé avec succée",HttpStatus.OK);
        }else{
            return new ResponseEntity("tahce introuvable",HttpStatus.NOT_FOUND);
        }
    }
}
