package com.onegateafrica.services;

import com.onegateafrica.Entity.DemandeEntity;
import com.onegateafrica.Entity.ProjectEntity;
import com.onegateafrica.Entity.UserEntity;
import com.onegateafrica.dto.ProjectDto;
import com.onegateafrica.dto.UserCreationDto;
import com.onegateafrica.exeption.NotFoundExeption;
import com.onegateafrica.repository.UserRepository;
import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.NotAcceptableStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.StandardCopyOption;
import java.util.*;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class UserServiceImpl  implements UserService{



    private final UserRepository userRepository;

    private final DemandeService demandeService;

    private PasswordEncoder encoder;


    public UserServiceImpl(UserRepository userRepository, DemandeService demandeService,
                           PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.demandeService = demandeService;
        this.encoder = encoder;
    }

    public List<UserEntity> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();

//        for (UserEntity ob : users){
//            if (ob.isStatus()==true){
//                users.remove(users.indexOf(ob));
//            }
//        }
        // get all active user without disabled
//        for (Iterator<UserEntity> i = users.iterator();i.hasNext();){
//            UserEntity user = i.next();
//            if (user.isDisabled()==true){
//                i.remove();
//            }
//        }

        return users;
    }

    @Override
    public List<UserEntity> getAllActifUsers() {
        List<UserEntity> users = userRepository.findAll();

//        for (UserEntity ob : users){
//            if (ob.isStatus()==true){
//                users.remove(users.indexOf(ob));
//            }
//        }
//         get all active user without disabled
        for (Iterator<UserEntity> i = users.iterator();i.hasNext();){
            UserEntity user = i.next();
            if (user.isDisabled()==true){
                i.remove();
            }
        }

        return users;
    }


    @SneakyThrows
    public UserEntity getUserByID(long id) {
        UserEntity user =  userRepository.findById(id)
                .orElseThrow(() -> new NotFoundExeption("user not found"+id));

        return  user;
    }

    @SneakyThrows
    @Override
    public List<UserEntity> getUserValidatedConjé() {
        List<UserEntity> users = userRepository.findAll();

        for (Iterator<UserEntity> i = users.iterator();i.hasNext();){
            UserEntity user = i.next();
            for (Iterator<DemandeEntity> j = user.getDemandes().iterator(); j.hasNext();){
                DemandeEntity demande = j.next();
                if (demande.getStatus()!= 2){
                    j.remove();
                }
                if (demande==null){
                    throw    new NotFoundExeption("this dos not exist");
                }
            }
            if (user.getDemandes()==null || user.isDisabled()== true){
                i.remove();
            }
//            if (user.getDemandes()){
//                i.remove();
//            }
        }
//        UserEntity user = userRepository.findById(id);
//        long userId = user.getId();
//        DemandeEntity demande = demandeService.getDemandeByid(userId);
//
        if (users!=null){
            return users;

        }
        else{
            throw    new NotFoundExeption("this user dos not exist");
        }


    }

    @Override
    public UserEntity getUserValidatedConjé(long id) {
        UserEntity user = getUserByID(id);

        for (Iterator<DemandeEntity> i = user.getDemandes().iterator();i.hasNext();){
            DemandeEntity demande = i.next();
            if (demande.getStatus()!= 1){
                i.remove();
            }
            if (demande==null){

            }

            if (user.getDemandes()==null || user.isDisabled()== true){
                i.remove();
            }
        }


        return user;
    }

    @Override
    public UserEntity getUserEnAttendeConjé(long id) {
        UserEntity user = getUserByID(id);

        for (Iterator<DemandeEntity> i = user.getDemandes().iterator();i.hasNext();){
            DemandeEntity demande = i.next();
            if (demande.getStatus()!= 0){
                i.remove();
            }

        }


        return user;
    }


    public UserEntity addUser(UserEntity user) {

        user.setUserName(user.getEmail());
       // user.setPassword(encoder.encode(user.getPassword()));

        return userRepository.save(user);
    }


    public UserEntity updateUser(UserEntity updateuser, long id) {

        UserEntity user = userRepository.findById(id)
                .orElseThrow(()-> new NotAcceptableStatusException("user not found"));
        user.setNom(updateuser.getNom());
        user.setPrenom(updateuser.getPrenom());
        user.setTelephone(updateuser.getTelephone());
        user.setEmail(updateuser.getEmail());
        user.setPassword(updateuser.getPassword());
        userRepository.save(user);

        return user;
    }


    public UserEntity disableUser(long id) {
        UserEntity user = getUserByID(id);
        user.setDisabled(true);
        userRepository.save(user);
        return user;
    }


    public List<UserEntity> getAllDisabledUsers() {
        List<UserEntity> users = userRepository.findAll();

        for (Iterator<UserEntity> i = users.iterator();i.hasNext();){
            UserEntity user = i.next();
            if (user.isDisabled()==false){
                i.remove();
            }
        }

        return users;
    }


    private static String storageDirectoryPath = System.getProperty("user.dir") + "/images/";
    @Override
    public String uploadImage(MultipartFile file) {
        makeDirectoryIfNotExist(storageDirectoryPath);
        Path storageDirectory = Paths.get(storageDirectoryPath);

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path destination = Paths.get(storageDirectory.toString() + "\\" + fileName);

        try {
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);// we are Copying all bytes from an input stream to a file

        } catch (IOException e) {
            e.printStackTrace();
        }

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/images/getImage/")
                .path(fileName)
                .toUriString();
        // return the download image url as a response entity
        String imageLink = destination.toString();
        return imageLink;
    }

    @Override
    public UserEntity getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Set<DemandeEntity> getListdemandeEmployee(long id) {

        UserEntity user = getUserByID(id);

        return user.getDemandes();
    }

    @Override
    public Set<DemandeEntity> getListdemandeEmployeeValider(long id) throws NotFoundExeption {
        UserEntity user = getUserByID(id);


        for (Iterator<DemandeEntity> i = user.getDemandes().iterator();i.hasNext();){
            DemandeEntity demande = i.next();
            if (demande.getStatus()!= 1){
                i.remove();
            }
        }
        if (user==null){
            throw new NotFoundExeption("not Found valide demande");
        }
            return user.getDemandes();



    }

    @Override
    public Set<DemandeEntity> getListdemandeEmployeeEnAttente(long id) throws NotFoundExeption {
        UserEntity user = getUserByID(id);


        for (Iterator<DemandeEntity> i = user.getDemandes().iterator();i.hasNext();){
            DemandeEntity demande = i.next();
            if (demande.getStatus()!= 0){
                i.remove();
            }
        }
        if (user==null){
            throw new NotFoundExeption("not Found en attente  demande");
        }
        return user.getDemandes();
    }

    @Override
    public Optional<UserEntity> getUserByIdResponse(long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserEntity addUserToProjects(long id, List<ProjectEntity> projects) {

        UserEntity user = getUserByID(id);
        if (user!=null){
            user.setProjects(projects);
            userRepository.save(user);
        }
        return user;
    }

    @Override
    public List<ProjectDto> getAllProjectsOfUser(long id) {
        UserEntity user = getUserByID(id);

        List<ProjectDto> projectList = new ArrayList<>();
        for (Iterator<ProjectEntity> i = user.getProjects().iterator();i.hasNext();){
            ProjectEntity project = i.next();
            if (project!=null){
                ProjectDto projectDto = new ProjectDto();
                projectDto.setId(project.getId());
                projectDto.setTitle(project.getTitle());
                projectDto.setDescription(project.getDescription());
                projectDto.setDateDebut(project.getDateDebut());
                projectDto.setTheme(project.getTheme());
                projectList.add(projectDto);
            }
        }
        return projectList;
    }

    @Override
    public List<UserEntity> getAllChedDeProjet() {
        List<UserEntity> users = userRepository.findAll();

        for (Iterator<UserEntity> i = users.iterator();i.hasNext();){
            UserEntity user = i.next();
            if (!user.getRole().equalsIgnoreCase("Chef de projet")){
                i.remove();
            }
        }

        return users;
    }

    @Override
    public void deleteUser(long userId) {

        userRepository.deleteById(userId);
    }

    private void makeDirectoryIfNotExist(String imageDirectory) {
        File directory = new File(imageDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
