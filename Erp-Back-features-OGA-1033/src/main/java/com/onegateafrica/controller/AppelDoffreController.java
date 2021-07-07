package com.onegateafrica.controller;

import com.onegateafrica.Entity.AppelDoffreEntity;
import com.onegateafrica.Entity.ProjectEntity;
import com.onegateafrica.services.AppelDoffreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
public class AppelDoffreController {


    private  AppelDoffreService appelDoffreService;

    @Autowired
    AppelDoffreController( AppelDoffreService appelDoffreService){
        this.appelDoffreService = appelDoffreService;
    }


    @GetMapping("appelDoffres")
    public List<AppelDoffreEntity> getAllAppelDoffres(){
        return appelDoffreService.getAllAppelDoffre();
    }


    @GetMapping("appelDoffre/{id}")
    public ResponseEntity<Optional<AppelDoffreEntity>> getProjectByIdForResponse(@PathVariable(name = "id")long id){

        Optional<AppelDoffreEntity> appelDoffre = appelDoffreService.getAppelDoffreByIdForResponse(id);
        if (appelDoffre.isPresent()){
            return new ResponseEntity(appelDoffre,HttpStatus.OK);
        }else{
            return new ResponseEntity("Appel d'offre introuvable",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("user/{id}/appelDoffre")
    public ResponseEntity<AppelDoffreEntity> saveAppelDoffre(@RequestBody AppelDoffreEntity appelDoffre,@PathVariable(name = "id") long id){
        return appelDoffreService.AjouterAppelDoffre(appelDoffre,id);
    }

    @PutMapping("appelDoffre/{id}")
    public ResponseEntity<AppelDoffreEntity> updateAppelDoffre(@PathVariable(value = "id") long id , @RequestBody AppelDoffreEntity newAppelDoffre){
        if (appelDoffreService.getAppelDoffre(id)!=null){
            return appelDoffreService.updateAppelDoffre(id,newAppelDoffre);
        }else{
            return new ResponseEntity("this appel d'offre dos not exist", HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("appelDoffre/{id}")
    public ResponseEntity<AppelDoffreEntity> deleteAppelDoffre (@PathVariable(value = "id") long id){
        if (appelDoffreService.getAppelDoffre(id)!=null){
            return appelDoffreService.deleteAppelDoffre(id);
        }else{
            return new ResponseEntity("this appel d'offre dos not exist", HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("refusAppelDoffre/{id}")
    public ResponseEntity<AppelDoffreEntity> refusAppelDoffre(@PathVariable(value = "id")long id){
        if (appelDoffreService.getAppelDoffre(id)!=null){
            return appelDoffreService.refuserAppelDoffre(id);
        }else{
            return new ResponseEntity("this appel d'offre dos not exist", HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("valedAppelDoffre/{id}")
    public ResponseEntity<AppelDoffreEntity> validerAppelDoffre(@PathVariable(value = "id")long id){
        if (appelDoffreService.getAppelDoffre(id)!=null){
            return  appelDoffreService.validerAppelDoffre(id);
        }else{
            return new ResponseEntity("this appel d'offre dos not exist", HttpStatus.NOT_FOUND);
        }

    }
}
