package com.onegateafrica.services;

import com.onegateafrica.Entity.DemandeEntity;
import com.onegateafrica.Entity.ProjectEntity;
import com.onegateafrica.Entity.UserEntity;
import com.onegateafrica.dto.ProjectDto;
import com.onegateafrica.dto.UserCreationDto;
import com.onegateafrica.exeption.NotFoundExeption;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface UserService {

    List<UserEntity> getAllUsers();

    List<UserEntity> getAllActifUsers();

    UserEntity getUserByID(long id);

    List<UserEntity> getUserValidatedConjé();

    UserEntity getUserValidatedConjé(long id);

    UserEntity getUserEnAttendeConjé(long id);

    UserEntity addUser(UserEntity user);

    UserEntity updateUser(UserEntity updateuser, long id);

    UserEntity disableUser(long id);

    List<UserEntity> getAllDisabledUsers();

    String uploadImage(MultipartFile file);

    UserEntity getUserByUserName(String userName);

    Set<DemandeEntity> getListdemandeEmployee(long id);

    Set<DemandeEntity> getListdemandeEmployeeValider(long id) throws NotFoundExeption;

    Set<DemandeEntity> getListdemandeEmployeeEnAttente(long id) throws NotFoundExeption;

    Optional<UserEntity> getUserByIdResponse(long id);

    UserEntity addUserToProjects(long id, List<ProjectEntity> projects);

    List<ProjectDto> getAllProjectsOfUser(long id);

    List<UserEntity> getAllChedDeProjet();

    void deleteUser(long userId);


}
