package com.onegateafrica.services;

import com.onegateafrica.Entity.ProjectEntity;
import com.onegateafrica.Entity.TacheEntity;
import com.onegateafrica.Entity.UserEntity;
import com.onegateafrica.repository.TacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class TacheServiceIpml implements TacheService{

    public TacheRepository tacheRepository;
    public  ProjectService projectService;
    public UserService userService;

    @Autowired
    public TacheServiceIpml(TacheRepository tacheRepository,ProjectService projectService,UserService userService) {
        this.tacheRepository = tacheRepository;
        this.projectService = projectService;
        this.userService = userService;
    }

    @Override
    public List<TacheEntity> getAllTaches() {
        return tacheRepository.findAll();
    }

    @Override
    public TacheEntity getTacheById(long id) {
        return tacheRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Not found"));
    }


    @Override
    public ResponseEntity<TacheEntity> addTache(long id, TacheEntity tache) {
        ProjectEntity project = projectService.getProjectById(id);

        if (project != null){
            tache.setProject(project);
            return new ResponseEntity<TacheEntity>(tacheRepository.save(tache), HttpStatus.CREATED);
        }else{
            return new ResponseEntity("this project dos not exist",HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<TacheEntity> updateTache(long id, TacheEntity newTache) {
        TacheEntity tache = getTacheById(id);
        if (tache != null){
            tache.setTitre(newTache.getTitre());
            tache.setDescription(newTache.getDescription());
            tache.setProgress(newTache.isProgress());

            tacheRepository.save(tache);

            return new ResponseEntity(tache,HttpStatus.OK);

        }else{
            return new ResponseEntity("This Tache does not exist",HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Boolean deleteTache(long id) {
        TacheEntity tache = getTacheById(id);
        Boolean result = false;
         tacheRepository.delete(tache);
         result = true;
         return result;
    }

    @Override
    public Optional<TacheEntity> getTacheByIdForResponse(long id) {
        return tacheRepository.findById(id);
    }

    @Override
    public TacheEntity addUsersToTache(long id, ArrayList<Long> users) {
        TacheEntity tache = getTacheById(id);

        List<UserEntity> userInDataBase = tache.getUsers();
        if (tache!=null){
            long i = 0;
            for (Iterator<Long> u = users.iterator(); u.hasNext();){
                long user = u.next();
                if (user!=0){
                    UserEntity utilisateur =  userService.getUserByID(user);



                    userInDataBase.add(utilisateur);


                }
                tache.setUsers(userInDataBase);

            }

            tacheRepository.save(tache);
        }
        return tache;
    }

    @Override
    public List<UserEntity> getUsersByTacheId(long id) {
        List<UserEntity> users = new ArrayList<>();

        TacheEntity tache = getTacheById(id);

        users = tache.getUsers();

        return users;
    }

    @Override
    public List<UserEntity> getAllNonAffectedUserToTache(long tacheId) {
        TacheEntity tache = getTacheById(tacheId);
        long projectId = tache.getProject().getId();
        List<UserEntity> users = projectService.getProjectById(projectId).getUsers();
        List<UserEntity> usersToRemove = new ArrayList<>();


        for (Iterator<UserEntity> u = users.iterator(); u.hasNext(); ) {
            UserEntity user = u.next();

            usersToRemove.addAll(exist(user.getId(), tacheId));




        }


            users.removeAll(usersToRemove);


            return users;


    }
    public List<UserEntity> exist(long userId,long tacheId){
        
        List<UserEntity> listUsers = new ArrayList<>();
        UserEntity user = userService.getUserByID(userId);
        boolean result=false;
        for (Iterator<TacheEntity> u = user.getTaches().iterator(); u.hasNext(); ) {
            TacheEntity tache = u.next();
            if (tache.getId() == tacheId){
                listUsers.add(user);
            }
        }
        return listUsers;

    }
}
