package com.onegateafrica.dto.getUserById;

import com.onegateafrica.Entity.UserEntity;
import com.onegateafrica.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProjectDtoForUserId {

    @Autowired
    private UserEntity userService;

    private long id;
    private String title;
    private String description;
    private String dateDebut;
    private String theme;
    private List<TacheDtoForUser> taches;


    public ProjectDtoForUserId(UserEntity userService, long id, String title, String description, String dateDebut, String theme, List<TacheDtoForUser> taches) {
        this.userService = userService;
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateDebut = dateDebut;
        this.theme = theme;
        this.taches = taches;
    }

    public ProjectDtoForUserId() {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<TacheDtoForUser> getTaches() {
        return taches;
    }

    public void setTaches(List<TacheDtoForUser> taches) {
        this.taches = taches;
    }
}
