package com.onegateafrica.dto.getUserById;

import java.util.List;

public class UserDtoById {
    private long id;
    private String userName;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String image;
    private String profileImage;
    private String telephone;
    private boolean disabled;
    private String  departement;
    private String sex;
    private String role;
    private List<ProjectDtoForUserId> projects;

    public UserDtoById() {

    }

    public long getUserDtoId(){
        return getId();
    }

    public UserDtoById(long id, String userName, String nom, String prenom, String email,String password, String image, String profileImage, String telephone, boolean disabled, String departement, String sex, String role, List<ProjectDtoForUserId> projects) {
        this.id = id;
        this.userName = userName;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.image = image;
        this.profileImage = profileImage;
        this.telephone = telephone;
        this.disabled = disabled;
        this.departement = departement;
        this.sex = sex;
        this.role = role;
        this.projects = projects;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<ProjectDtoForUserId> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDtoForUserId> projects) {
        this.projects = projects;
    }
}
