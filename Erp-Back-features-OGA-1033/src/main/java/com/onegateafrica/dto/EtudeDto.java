package com.onegateafrica.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onegateafrica.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EtudeDto {

    private long id;
    private String referenceProjet; //référence de projet
    private String datepost; // la date de l'ajout de l'étude
    private  String titre; // titre de l'etude
    private String description;
    private int status; // ( 0 => en attente - 1 => validé - 2 => réfusé )
    private String fileName;
    private String imageUri;
    private UserDto userEntity;
    private AppelDoffreDto appelDoffre;

}
