package com.onegateafrica.dto;

import com.onegateafrica.Entity.EtudeEntity;
import com.onegateafrica.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
public class EtudeCreationDto {

    private EtudeEntity etudeEntity;

    private String imageUri;

    private String message;

    public EtudeCreationDto(EtudeEntity etudeEntity, String imageUri, String message) {
        this.etudeEntity = etudeEntity;
        this.imageUri = imageUri;
        this.message = message;
    }

    public EtudeEntity getEtudeEntity() {
        return etudeEntity;
    }

    public void setEtudeEntity(EtudeEntity etudeEntity) {
        this.etudeEntity = etudeEntity;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

