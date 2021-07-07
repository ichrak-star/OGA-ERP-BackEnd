package com.onegateafrica.services;

import com.onegateafrica.Entity.DemandeEntity;
import com.onegateafrica.Entity.EtudeEntity;

import java.util.List;
import java.util.Optional;

public interface DemandeService {
    DemandeEntity ajouterDemande(DemandeEntity demande);

    List<DemandeEntity> getDemandes();

    DemandeEntity getDemandeByid(long id);

    DemandeEntity rhValidation(long id);

    DemandeEntity rhRefus(long id);

    DemandeEntity updateDemande(DemandeEntity demande, long id);

    List<DemandeEntity> valideDemande();

    List<DemandeEntity> RefusedDemane();

    List<DemandeEntity> enAttenteDemane();
    public Optional<DemandeEntity> getDemandeByIdForResponse(long id);



}
