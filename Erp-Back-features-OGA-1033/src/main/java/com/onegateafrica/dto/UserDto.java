package com.onegateafrica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private long id;
    private String userName;
    private String nom;
    private String prenom;
    private String email;
    private String image;
    private String profileImage;
    private String telephone;
    private boolean disabled;
    private String  departement;
    private String sex;
    private String role;

    public long getUserDtoId(){
        return getId();
    }


}
