package com.onegateafrica.controller;


import com.onegateafrica.Entity.AppelDoffreEntity;
import com.onegateafrica.Entity.DemandeEntity;
import com.onegateafrica.Entity.UserEntity;
import com.onegateafrica.Utile.NotificationsService;
import com.onegateafrica.dto.DemandeDto;
import com.onegateafrica.repository.DemandeRepository;
import com.onegateafrica.services.DemandeService;
import com.onegateafrica.services.UserService;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class DemandeController {

  @Autowired
  private final DemandeService demandeService;
  private final UserService userService;
  private final NotificationsService notificationsService;



//  public DemandeController(DemandeService demandeService, UserService userService,NotificationsService notificationsService) {
//    this.demandeService = demandeService;
//    this.userService = userService;
//    this.notificationsService = notificationsService;
//  }

  @GetMapping("demandes")
  public List<DemandeEntity> getDemandes() {
    return demandeService.getDemandes();
  }

  @GetMapping("validedemandes")
  public List<DemandeEntity> getValideDemandes() {
    return demandeService.valideDemande();
  }

  @GetMapping("refuseddemandes")
  public List<DemandeEntity> getRefusedDemandes() {
    return demandeService.RefusedDemane();
  }

  @GetMapping("enattentedemandes")
  public List<DemandeEntity> getEnAttenteDemandes() {
    return demandeService.enAttenteDemane();
  }


  @GetMapping("demande/{id}")
  public ResponseEntity<Optional<DemandeEntity>> getProjectByIdForResponse(@PathVariable(name = "id")long id){

    Optional<DemandeEntity> demandeEntity = demandeService.getDemandeByIdForResponse(id);
    if (demandeEntity.isPresent()){
      return new ResponseEntity(demandeEntity,HttpStatus.OK);
    }else{
      return new ResponseEntity("Demande introuvable",HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("demande/{id}")
  public DemandeEntity updateDemande(@RequestBody DemandeEntity demande,@PathVariable(value = "id") long id) {
    DemandeEntity oldDemande = demandeService.getDemandeByid(id);

    oldDemande.setStatus(demande.getStatus());
    oldDemande.setCommentaireGrh(demande.getCommentaireGrh());

    return demandeService.ajouterDemande(oldDemande);

  }

  @PutMapping("rhValide/{id}")
  public DemandeEntity rhValide(@PathVariable(value = "id") long id ) {
    return demandeService.rhValidation(id);
  }

  @PutMapping("rhrefus/{id}")
  public DemandeEntity rhrefus(@PathVariable(value = "id") long id ) {
    return demandeService.rhRefus(id);
  }


}
