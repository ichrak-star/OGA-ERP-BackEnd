package com.onegateafrica.Entity;


import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")

public class UserEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String userName;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private MultipartFile[] imageFile;
    private String image;
    private String profileImage;
    private String telephone;
    private boolean disabled=false;
    private String  departement;
    private String sex;
    private String role;
    private String[] roles;
    private String[] authorities;




    @OneToMany(
            mappedBy = "userEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true

    )
    private Set<DemandeEntity> demandes ;

    @OneToMany(
            mappedBy = "userEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<AppelDoffreEntity> appelDoffreEntities ;


    @OneToMany(
            mappedBy = "userEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private Set<QualityEntity> qualitys ;

    @OneToMany(
            mappedBy = "userEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<EtudeEntity> etudes;

//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                    CascadeType.PERSIST,
//                    CascadeType.MERGE,
//            })
//    @JoinTable(name = "user_projects",
//            joinColumns = { @JoinColumn(name = "user_id") },
//            inverseJoinColumns = { @JoinColumn(name = "project_id") })
//    private List<ProjectEntity> projects = new ArrayList<ProjectEntity>();
        @ManyToMany(fetch = FetchType.LAZY,
                    cascade = {
                            CascadeType.PERSIST,
                            CascadeType.MERGE
                    },
                    mappedBy = "users")

//        @JsonBackReference


        private List<ProjectEntity> projects = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "users")

//        @JsonBackReference
    @JsonIgnore
    private List<TacheEntity> taches = new ArrayList<>();



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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public MultipartFile[] getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile[] imageFile) {
        this.imageFile = imageFile;
    }

    public Set<DemandeEntity> getDemandes() {
        return demandes;
    }

    public void setDemandes(Set<DemandeEntity> demandes) {
        this.demandes = demandes;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String[] getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String[] authorities) {
        this.authorities = authorities;
    }

    public Set<AppelDoffreEntity> getAppelDoffreEntities() {
        return appelDoffreEntities;
    }

    public void setAppelDoffreEntities(Set<AppelDoffreEntity> appelDoffreEntities) {
        this.appelDoffreEntities = appelDoffreEntities;
    }

    public Set<QualityEntity> getQualitys() {
        return qualitys;
    }

    public void setQualitys(Set<QualityEntity> qualitys) {
        this.qualitys = qualitys;
    }

    public List<EtudeEntity> getEtudes() {
        return etudes;
    }

    public void setEtudes(List<EtudeEntity> etudes) {
        this.etudes = etudes;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", imageFile=" + Arrays.toString(imageFile) +
                ", image='" + image + '\'' +
                ", telephone=" + telephone +
                ", disabled=" + disabled +
                ", departement='" + departement + '\'' +
                ", sex='" + sex + '\'' +
                ", role='" + role + '\'' +
                ", demandes=" + demandes +
                '}';
    }
}
