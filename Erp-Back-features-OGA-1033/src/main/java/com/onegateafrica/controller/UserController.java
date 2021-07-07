package com.onegateafrica.controller;

import com.onegateafrica.Entity.DemandeEntity;
import com.onegateafrica.Entity.ProjectEntity;
import com.onegateafrica.Entity.TacheEntity;
import com.onegateafrica.Entity.UserEntity;

import com.onegateafrica.dto.*;
import com.onegateafrica.dto.getUserById.ProjectDtoForUserId;
import com.onegateafrica.dto.getUserById.TacheDtoForUser;
import com.onegateafrica.dto.getUserById.UserDtoById;
import com.onegateafrica.exeption.NotFoundExeption;
import com.onegateafrica.services.DemandeService;
import com.onegateafrica.services.ImageService;
import com.onegateafrica.services.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api")
@NoArgsConstructor
public class UserController {



    private UserService userService;


    private ImageService imageService;

    private DemandeService demandeService;








    @Autowired
    public UserController(UserService userService, ImageService imageService,DemandeService demandeService) {
        this.userService = userService;
        this.imageService = imageService;
        this.demandeService = demandeService;
    }






    @GetMapping("/home")
    public String greeting(){

        return "Hello mouez";
    }

    @GetMapping("users")
    public List<UserEntity> getAllUsers(){

        return userService.getAllUsers();
    }

//   @GetMapping("getUserValidatedConjé)
//    public List<UserEntity> getUserValidaedDemande(){
//       UserEntity user = userService.getUserValidatedConjé();
//
//       return user;
//       }


    @GetMapping("userAllProjects/{id}")
    public List<ProjectDto> getAlluserProjects(@PathVariable(value = "id")long id){
        return userService.getAllProjectsOfUser(id);
    }

    @GetMapping("allChefDeProjet")
    public List<UserEntity> getAllChefDeProjet(){
        return userService.getAllChedDeProjet();
    }

    @GetMapping("disabledusers")
    public List<UserEntity> getAllDisabledUser(){
        return userService.getAllDisabledUsers();
    }

    @GetMapping("actifusers")
    public List<UserEntity> getAllActifUsers(){
        return userService.getAllActifUsers();
    }

    @GetMapping("usersvalidconje")
    public List<UserEntity> getAllUserValidtedConje(){
        return userService.getUserValidatedConjé();
    }

    @GetMapping("user/{id}")
    public ResponseEntity<UserDtoById> getUserById(@PathVariable(value = "id") long id){


        Optional<UserEntity> userEntityOptional = userService.getUserByIdResponse(id);
        UserEntity user = userService.getUserByID(id);
        List<ProjectEntity> projects = user.getProjects();
        UserDtoById userDtoById = new UserDtoById();
        ProjectDtoForUserId projectUser = new ProjectDtoForUserId();
        List<ProjectDtoForUserId> projectsDto = new ArrayList<>();
        TacheDtoForUser tacheDtoForUser = new TacheDtoForUser();
        List<TacheDtoForUser> tacheDtoForUserList = new ArrayList<>();

        List<ProjectEntity> projectsToRemove = new ArrayList<>();
        List<TacheEntity> tachesToRemove = new ArrayList<>();
        List<TacheEntity> taches = user.getTaches();


        if (userEntityOptional.isPresent()){

            for (Iterator<TacheEntity> i = user.getTaches().iterator(); i.hasNext();){

                TacheEntity tache = i.next();
                for (Iterator<UserEntity> j = tache.getUsers().iterator(); j.hasNext();){
                    UserEntity userEntity = j.next();
                    if (user.getId() == userEntity.getId()){
                        tacheDtoForUser.setId(tache.getId());
                        tacheDtoForUser.setTitre(tache.getTitre());
                        tacheDtoForUser.setDescription(tache.getDescription());
                        tacheDtoForUser.setProgress(tache.isProgress());



                            tacheDtoForUserList.add(tacheDtoForUser);


                    }

                }

            }


            for (Iterator<ProjectEntity> i = user.getProjects().iterator(); i.hasNext();){

                    ProjectEntity project = i.next();
                for (Iterator<UserEntity> j = project.getUsers().iterator(); j.hasNext();){

                    UserEntity userEntity = j.next();
                    if (user.getId() == userEntity.getId()&& project.getId()!=projectUser.getId()){
                        projectUser.setId(project.getId());
                        projectUser.setTitle(project.getTitle());
                        projectUser.setDescription(project.getDescription());
                        projectUser.setDateDebut(project.getDateDebut());
                        projectUser.setTheme(project.getTheme());
                        projectUser.setTaches(tacheDtoForUserList);

                        projectsDto.add(projectUser);
                    }
                }


            }

            userDtoById.setId(user.getId());
            userDtoById.setUserName(user.getUserName());
            userDtoById.setNom(user.getNom());
            userDtoById.setPrenom(user.getPrenom());
            userDtoById.setEmail(user.getEmail());
            userDtoById.setPassword(user.getPassword());
            userDtoById.setImage(user.getImage());
            userDtoById.setProfileImage(user.getProfileImage());
            userDtoById.setTelephone(user.getTelephone());
            userDtoById.setDisabled(user.isDisabled());
            userDtoById.setDepartement(user.getDepartement());
            userDtoById.setSex(user.getSex());
            userDtoById.setRole(user.getRole());
            userDtoById.setProjects(projectsDto);
//            projects.removeAll(projectsToRemove);
//
//            user.setProjects(projects);

//            for (Iterator<TacheEntity> i = user.getTaches().iterator(); i.hasNext();){
//                TacheEntity tache = i.next();
//
//                for (Iterator<UserEntity> j = tache.getUsers().iterator(); j.hasNext();){
//                    UserEntity userInTache = j.next();
//
//                    if (id != userInTache.getId()){
//                        tachesToRemove.add(tache);
//                    }
//                }
//            }

//            taches.removeAll(tachesToRemove);
//            user.setTaches(taches);



                return new ResponseEntity(userDtoById,HttpStatus.OK) ;

        }else {
             return new ResponseEntity("Utilisateur introuvable",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("uservalidconje/{id}")
    public UserEntity getUserValidatedConjeById(@PathVariable(value = "id") long id){
        return userService.getUserValidatedConjé(id);

    }

    @GetMapping("userenattentedemande/{id}")
    public UserEntity getUserEnAttenteDemande(@PathVariable(value = "id") long id){
        return userService.getUserEnAttendeConjé(id);

    }

    @GetMapping("listedemandesemployee/{id}")
    public Set<DemandeEntity> getListDemandeEmployee(@PathVariable(value = "id") long id){
        return userService.getListdemandeEmployee(id);

    }

    @GetMapping("listedemandesemployeevalider/{id}")
    public Set<DemandeEntity> getListDemandeEmployeeValider(@PathVariable(value = "id") long id) throws NotFoundExeption {
        return userService.getListdemandeEmployeeValider(id);

    }
@GetMapping("listedemandesemployeeenattente/{id}")
    public Set<DemandeEntity> getListDemandeemplyeeenattente(@PathVariable(value = "id") long id) throws NotFoundExeption {
        return userService.getListdemandeEmployeeEnAttente(id);


    }


    @GetMapping(value = "/userImage/{phoneNumber}")
    public   byte[] getUserCINPicture( @RequestParam("phoneNumber") String phoneNumber
    ) throws IOException {
        UserEntity user = userService.getUserByUserName(phoneNumber);

        String image = user.getImage();
        return imageService.getImage(image);

    }

    @GetMapping(value = "/image")
    public @ResponseBody byte[] getImage() throws IOException {
        FileInputStream in = new FileInputStream("file:///C://Users//Mouez//Downloads//ERPCurentAjourdemo//ERPCurentAjour//ERPCurent//ERP//images//manel.PNG");
//        InputStream in = getClass()
//                .getResourceAsStream("file:///C://Users//Mouez//Downloads//ERPCurentAjourdemo//ERPCurentAjour//ERPCurent//ERP//images//manel.PNG");
        return IOUtils.toByteArray(in);
    }



    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public ResponseEntity<UserCreationDto> addUser(@ModelAttribute   UserEntity user, MultipartFile file){

//        if (userService.getUserByUserName(user.getUserName())!=null){
//            return new ResponseEntity(new UserCreationDto(user,"null","this "),HttpStatus.BAD_REQUEST);
//        }
        String fileName = imageService.storeFile(file);
//        String profileImageName = imageService.storeFile(profileImageFile);

//        String profileImageName = null;

        user.setImage(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/downloadFile/")
                .path(fileName)
                .toUriString());
//        user.setProfileImage(ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/api/downloadFile/")
//                .path(profileImageName)
//                .toUriString());
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return new ResponseEntity(new UserCreationDto(userService.addUser(user),"user succsusfully added to database"),HttpStatus.CREATED);
    }
    @GetMapping("downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        // Load file as Resource
        Resource resource = imageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;

            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    @PostMapping("user/{userId}/demande")
    public ResponseEntity<DemandeEntity> ajouterDemande(@PathVariable(value = "userId") long userId, @RequestBody DemandeEntity demande) {

        UserEntity user = userService.getUserByID(userId);


        demande.setIdEmploye(user.getId());
        demande.setUserEntity(user);


        return new ResponseEntity<DemandeEntity>(demandeService.ajouterDemande(demande),HttpStatus.CREATED);
        //return new ResponseEntity(new DemandeDto(demande,"la Demande pour l'utilisateur "+user.getUserName()+" est crée avec succéss"),HttpStatus.CREATED);
//    DemandeEntity demandeuser = demandeService.ajouterDemande(demande);
//    ResponseEntity notification = new ResponseEntity<> (new DemandeDto(demandeService.ajouterDemande(demande),notificationsService.sendNotification(user.getId())), HttpStatus.OK);
//    System.out.println(notification);
//    return notification;
    }
    @PostMapping("user/{id}/projects")
    public UserEntity addProjectsToUser(@PathVariable(value = "id")long id, @RequestBody List<ProjectEntity> projects){
        return userService.addUserToProjects(id,projects);
    }


    @PutMapping("user/{id}")
    public UserEntity updateUser(@PathVariable(value = "id") long id, @RequestBody   UserEntity user){
        UserEntity foundUser = userService.getUserByID(id);

        //foundUser.setImage(imageService.storeFile(file));
        foundUser.setUserName(user.getEmail());
        foundUser.setPassword(user.getPassword());
        foundUser.setEmail(user.getEmail());
        foundUser.setDepartement(user.getDepartement());
        foundUser.setTelephone(user.getTelephone());
        foundUser.setNom(user.getNom());
        foundUser.setPrenom(user.getPrenom());
        foundUser.setRole(user.getRole());
        foundUser.setSex((user.getSex()));



//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/api/downloadFile/")
//                .path(imageService.storeFile(file))
//                .toUriString();

        return userService.addUser(foundUser);

    }
    @RequestMapping(value = "user/{id}/updateCin", method = RequestMethod.PUT, consumes = { "multipart/form-data" })
    public UserEntity updateUserCin(@PathVariable(value = "id") long id, @ModelAttribute MultipartFile file){

        UserEntity foundUser = userService.getUserByID(id);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/downloadFile/")
                .path(imageService.storeFile(file))
                .toUriString();


        foundUser.setImage(fileDownloadUri);
        return userService.addUser(foundUser);
    }
    @RequestMapping(value = "user/{id}/updateProfileImage", method = RequestMethod.PUT, consumes = { "multipart/form-data" })
    public UserEntity updateUserProfileImage(@PathVariable(value = "id") long id, @ModelAttribute MultipartFile file){

        UserEntity foundUser = userService.getUserByID(id);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/downloadFile/")
                .path(imageService.storeFile(file))
                .toUriString();


        foundUser.setProfileImage(fileDownloadUri);
        return userService.addUser(foundUser);
    }

    @DeleteMapping("user/{id}")
    public UserEntity disableUser(@PathVariable(value = "id") long id){

        return userService.disableUser(id);
    }



}