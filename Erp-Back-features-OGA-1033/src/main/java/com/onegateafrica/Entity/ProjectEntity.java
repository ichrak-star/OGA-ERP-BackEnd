package com.onegateafrica.Entity;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler"})
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    private String title;
    private String description;
    private String dateDebut;
    private String theme;

    @OneToMany(mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<QualityEntity> qualitys;

    @OneToMany(mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true)


    private List<TacheEntity> taches;

//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                    CascadeType.PERSIST,
//                    CascadeType.MERGE
//            },
//            mappedBy = "projects")
//    private List<UserEntity> users = new ArrayList<>();
@ManyToMany(fetch = FetchType.LAZY,
        cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE,
        })
@JoinTable(name = "user_project",
        joinColumns = { @JoinColumn(name = "project_id") },
        inverseJoinColumns = { @JoinColumn(name = "user_id") })
@JsonIgnore
private List<UserEntity> users = new ArrayList<UserEntity>();


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public List<QualityEntity> getQualitys() {
        return qualitys;
    }

    public void setQualitys(List<QualityEntity> qualitys) {
        this.qualitys = qualitys;
    }

    public List<TacheEntity> getTaches() {
        return taches;
    }

    public void setTaches(List<TacheEntity> taches) {
        this.taches = taches;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
