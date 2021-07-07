package com.onegateafrica.services;

import com.onegateafrica.Entity.ProjectEntity;
import com.onegateafrica.Entity.TacheEntity;
import com.onegateafrica.Entity.UserEntity;
import com.onegateafrica.dto.UserDto;
import com.onegateafrica.exeption.NotFoundExeption;
import com.onegateafrica.repository.ProjectRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;
    private UserService userService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }


    @Override
    public List<ProjectEntity> getAllProject() {
        return projectRepository.findAll();
    }

    @Override
    public ProjectEntity addProject(long id, ProjectEntity project) {
        UserEntity user = userService.getUserByID(id);

        List<UserEntity> userList = new ArrayList<>();
        userList.add(user);

        project.setUsers(userList);

        return projectRepository.save(project);
    }

    @Override
    @SneakyThrows
    public ProjectEntity getProjectById(long id) {


        ProjectEntity project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundExeption("user not found" + id));

        return project;

    }

    @Override
    public ProjectEntity updateproject(long id, ProjectEntity project) {
        ProjectEntity oldProject = getProjectById(id);

        oldProject.setTitle(project.getTitle());
        oldProject.setDescription(project.getDescription());
        oldProject.setDateDebut(project.getDateDebut());

        return projectRepository.save(oldProject);
    }

    @Override
    public ResponseEntity<ProjectEntity> deleteProjetctById(long id) {

        ProjectEntity project = getProjectById(id);
        projectRepository.delete(project);

        return new ResponseEntity("le projet a bien été supprimer", HttpStatus.OK);
    }

    @Override
    public Optional<ProjectEntity> getprojectByIdForResponse(long id) {
        return projectRepository.findById(id);
    }

    @Override
    public List<UserDto> getAllUsersOfProject(long id) {
        ProjectEntity project = getProjectById(id);

        List<UserDto> userList = new ArrayList<>();
        for (Iterator<UserEntity> i = project.getUsers().iterator(); i.hasNext(); ) {
            UserEntity user = i.next();
            if (user != null) {
                UserDto userDto = new UserDto();
                userDto.setId(user.getId());
                userDto.setUserName(user.getUserName());
                userDto.setEmail(user.getEmail());
                userDto.setDepartement(userDto.getDepartement());
                userDto.setNom(user.getNom());
                userDto.setPrenom(user.getPrenom());
                userDto.setTelephone(user.getTelephone());
                userDto.setImage(user.getImage());
                userDto.setProfileImage(user.getProfileImage());
                userDto.setSex(user.getSex());
                userDto.setRole(user.getRole());
                userDto.setDisabled(user.isDisabled());

                userList.add(userDto);
            }

        }
        return userList;
    }

    @Override
    public ProjectEntity addUsersToProject(long id, ArrayList<Long> users) {
        ProjectEntity project = getProjectById(id);

        List<UserEntity> userInDataBase = project.getUsers();
        if (project != null) {
            long i = 0;
            for (Iterator<Long> u = users.iterator(); u.hasNext(); ) {
                long user = u.next();
                if (user != 0) {
                    UserEntity utilisateur = userService.getUserByID(user);


                    userInDataBase.add(utilisateur);


                }

            }
            project.setUsers(userInDataBase);
            projectRepository.save(project);
        }
        return project;
    }

    @Override
    public List<TacheEntity> getTachesByUserIdAndProjectId(long userId, long projectId) {
        List<TacheEntity> listTache = new ArrayList<>();
        List<UserEntity> users = projectRepository.findById(projectId).get().getUsers();

        for (Iterator<UserEntity> u = users.iterator(); u.hasNext(); ) {
            UserEntity user = u.next();
            List<TacheEntity> taches = userService.getUserByID(userId).getTaches();


            listTache.addAll(taches);
            break;
        }

        return listTache;
    }

    @Override
    public List<UserEntity> getAllNonAffectedUserTopPoject(long projectId) {
        List<UserEntity> usersToRemove = new ArrayList<>();
        List<UserEntity> users = userService.getAllUsers();
        for (Iterator<UserEntity> u = users.iterator(); u.hasNext(); ) {
            UserEntity user = u.next();

            for (Iterator<ProjectEntity> j = user.getProjects().iterator(); j.hasNext(); ) {
                ProjectEntity project = j.next();
                if (project.getId() == projectId) {
                    usersToRemove.add(user);
                }
            }

        }
        users.removeAll(usersToRemove);
        return users;
    }

    @Override
    public void deleteUserFromProject(long projectId, long userId) {
     List<UserEntity> users = getProjectById(projectId).getUsers();
     UserEntity user = userService.getUserByID(userId);

        for (Iterator<UserEntity> j = users.iterator(); j.hasNext(); ) {
            UserEntity userEntity = j.next();
            if (userEntity == user){
                userService.deleteUser(userId);
            }
        }

    }
}
