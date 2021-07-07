package com.onegateafrica.dto;

import com.onegateafrica.Entity.EtudeEntity;
import com.onegateafrica.Entity.QualityEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QualityCreationDto {


    private long id;
    private String projectReference;
    private String description;
    private String title;
    private String fileName;
    private String projectTitle;
    private UserDto userEntity;
    private String imageUri;


}