package com.onegateafrica.services;

import com.onegateafrica.Entity.EtudeEntity;
import com.onegateafrica.Entity.ProjectEntity;
import com.onegateafrica.Entity.TacheEntity;
import com.onegateafrica.Entity.UserEntity;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface TacheService {

    public List<TacheEntity> getAllTaches();

    public TacheEntity getTacheById(long id);


    public ResponseEntity<TacheEntity> addTache(long id, TacheEntity tache);

    public ResponseEntity<TacheEntity> updateTache(long id, TacheEntity newTache);

    public Boolean deleteTache(long id);

    public Optional<TacheEntity> getTacheByIdForResponse(long id);

    public TacheEntity addUsersToTache(long id, ArrayList<Long> users);

    public List<UserEntity> getUsersByTacheId(long id);

    List<UserEntity> getAllNonAffectedUserToTache(long tacheId);
}
