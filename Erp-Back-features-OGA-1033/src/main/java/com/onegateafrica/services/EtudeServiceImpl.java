package com.onegateafrica.services;


import com.onegateafrica.Entity.AppelDoffreEntity;
import com.onegateafrica.Entity.EtudeEntity;
import com.onegateafrica.Entity.UserEntity;
import com.onegateafrica.repository.EtudeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class EtudeServiceImpl implements EtudeService {
    private EtudeRepository etudeRepository;
    private UserService userService;
    private AppelDoffreService appelDoffreService;

    public EtudeServiceImpl(EtudeRepository etudeRepository, UserService userService,AppelDoffreService appelDoffreService){
        this.etudeRepository = etudeRepository;
        this.userService = userService;
        this.appelDoffreService = appelDoffreService;
    }

    @Override
    public ResponseEntity<EtudeEntity> creerEtude(EtudeEntity etude, long id,long appelDoffreId){
        UserEntity user = userService.getUserByID(id);
        AppelDoffreEntity appelDoffre = appelDoffreService.getAppelDoffre(appelDoffreId);

        if (user != null && appelDoffre!=null){
            etude.setUserEntity(user);
            etude.setAppelDoffre(appelDoffre);
            return new ResponseEntity<EtudeEntity>(etudeRepository.save(etude), HttpStatus.CREATED);
        }else{
            return new ResponseEntity("this user or appel d'offre dos not exist",HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<EtudeEntity> getAllEtudes() {
        return etudeRepository.findAll();
    }

    @Override
    public EtudeEntity getEtudeById(Long id) {
        return etudeRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Not found"));
    }

    @Override
    public ResponseEntity<EtudeEntity> updateEtude(Long id, EtudeEntity newEtude) {
        EtudeEntity etude = getEtudeById(id);
        if (etude != null){
            etude.setReferenceProjet(newEtude.getReferenceProjet());
            etude.setTitre(newEtude.getTitre());
            etude.setDatepost(newEtude.getDatepost());
            etude.setDescription(newEtude.getDescription());
            etude.setStatus(newEtude.getStatus());

            etudeRepository.save(etude);

            return new ResponseEntity(etude,HttpStatus.OK);

        }else{
            return new ResponseEntity("This Etude does not exist",HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Optional<EtudeEntity> getEtudeByIdForResponse(long id) {
        return etudeRepository.findById(id);
    }

}
