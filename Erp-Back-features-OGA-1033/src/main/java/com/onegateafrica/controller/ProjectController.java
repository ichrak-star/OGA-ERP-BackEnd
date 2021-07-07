package com.onegateafrica.controller;

import com.onegateafrica.Entity.ProjectEntity;
import com.onegateafrica.Entity.TacheEntity;
import com.onegateafrica.Entity.UserEntity;
import com.onegateafrica.dto.ProjectDto;
import com.onegateafrica.dto.TacheDto;
import com.onegateafrica.dto.UserDto;
import com.onegateafrica.services.ProjectService;
import com.onegateafrica.services.TacheService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProjectController {

    private ProjectService projectService;
    private TacheService tacheService;

    @Autowired
    public ProjectController(ProjectService projectService,TacheService tacheService) {
        this.projectService = projectService;
        this.tacheService = tacheService;
    }

    @GetMapping("project")
    public List<ProjectEntity> getAllProject(){
        return projectService.getAllProject();
    }


    @GetMapping("project/{projectId}/user/{userId}/taches")
    public List<TacheEntity> getAllTaches(@PathVariable(value = "projectId")long projectId,@PathVariable(value = "userId")long userId){
        return projectService.getTachesByUserIdAndProjectId(userId,projectId);
    }

    @GetMapping("projectAllUsers/{id}")
    public List<UserDto> getAllprojectUsers(@PathVariable(value = "id")long id){
        return projectService.getAllUsersOfProject(id);
    }
    @GetMapping("nonAffectedUsersToProject/{id}")
    public List<UserEntity> getAllUsersNonAffected(@PathVariable(value = "id")long id){
        return projectService.getAllNonAffectedUserTopPoject(id);
    }
    @DeleteMapping("project/{projectId}/user/{userId}")
    public ResponseEntity deleteUserFromProject(@PathVariable(value = "projectId")long projectId,@PathVariable(value = "userId")long userId){
        projectService.deleteUserFromProject(projectId,userId);
        return new ResponseEntity("User "+userId + " is deleted",HttpStatus.OK);
    }

    @GetMapping("project/{id}")
    public ResponseEntity<ProjectDto> getProjectByIdForResponse(@PathVariable(name = "id")long id){

        Optional<ProjectEntity> projectEntity = projectService.getprojectByIdForResponse(id);
        ProjectEntity project = projectService.getProjectById(id);
        if (projectEntity.isPresent()){
            ProjectDto projectDto = new ProjectDto();
            projectDto.setId(project.getId());
            projectDto.setTheme((project.getTheme()));
            projectDto.setDateDebut(project.getDateDebut());
            projectDto.setDescription(project.getDescription());
            projectDto.setTitle(project.getTitle());
            UserDto user = new UserDto();

            List<UserDto> users = new ArrayList<>();
            List<TacheDto> taches = new ArrayList<>();
            List<UserDto> usersInTache = new ArrayList<>();

            for (Iterator<UserEntity> i = project.getUsers().iterator(); i.hasNext();){
                UserEntity utilisateur = i.next();
                if (utilisateur != null){

                }

                users.add(new UserDto(utilisateur.getId(),
                        utilisateur.getUserName(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                        utilisateur.getEmail(),
                        utilisateur.getImage(),
                        utilisateur.getProfileImage(),
                        utilisateur.getTelephone(),
                utilisateur.isDisabled(),
                        utilisateur.getDepartement(),
                        utilisateur.getSex(),
                        utilisateur.getRole()

                ));
            }
            projectDto.setUserDto(users);

            for (Iterator<TacheEntity> i = project.getTaches().iterator(); i.hasNext();){
                TacheEntity tache = i.next();

                    for (Iterator<UserEntity> j = tache.getUsers().iterator(); j.hasNext();) {
                        UserEntity userInTache = j.next();
                        usersInTache.add(new UserDto(userInTache.getId(),
                                userInTache.getUserName(),
                                userInTache.getNom(),
                                userInTache.getPrenom(),
                                userInTache.getEmail(),
                                userInTache.getImage(),
                                userInTache.getProfileImage(),
                                userInTache.getTelephone(),
                                userInTache.isDisabled(),
                                userInTache.getDepartement(),
                                userInTache.getSex(),
                                userInTache.getRole()
                        ));
                    }
                    taches.add(new TacheDto(tache.getId(),
                            tache.getTitre(),
                            tache.getDescription(),
                            tache.isProgress(),
                            usersInTache
                    ));
            }

            projectDto.setTahces(taches);

            return new ResponseEntity(projectDto,HttpStatus.OK);
        }else{
            return new ResponseEntity("projet introuvable",HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("project/{id}/users")
    public ProjectEntity addProjectToUsers(@PathVariable(value = "id")long id, @RequestBody ArrayList<Long> users){
        return projectService.addUsersToProject(id,users);
    }

    @PostMapping("project/{userId}")
    public ResponseEntity<ProjectEntity> addProject(@PathVariable(value = "userId")long userId,@RequestBody ProjectEntity project){
        if (project!=null){
            return new ResponseEntity<ProjectEntity>(projectService.addProject(userId,project), HttpStatus.CREATED);
        }else{
            return new ResponseEntity("Vous devez remplir les information du projet",HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("project/{id}")
    public ResponseEntity<ProjectEntity> updateProject(@PathVariable(name = "id")long id,@RequestBody ProjectEntity newproject){
        ProjectEntity project = projectService.getProjectById(id);
        if (project!=null){
            return new ResponseEntity<ProjectEntity>(projectService.updateproject(id,newproject),HttpStatus.OK);
        }else{
            return new ResponseEntity("Projet introuvable",HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("project/{id}")
    public ResponseEntity<ProjectEntity> updateproject(@PathVariable(name = "id")long id){
        ProjectEntity project = projectService.getProjectById(id);
        if (project!=null){
            return new ResponseEntity(projectService.deleteProjetctById(id),HttpStatus.OK);
        }else{
            return new ResponseEntity("projet introuvable",HttpStatus.NOT_FOUND);
        }
    }
}
