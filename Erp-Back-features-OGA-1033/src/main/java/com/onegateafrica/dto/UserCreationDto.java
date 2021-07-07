package com.onegateafrica.dto;

import com.onegateafrica.Entity.UserEntity;

public class UserCreationDto {

    private UserEntity user;


    private String message;

    public UserCreationDto(UserEntity user, String message) {
        this.user = user;

        this.message = message;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
