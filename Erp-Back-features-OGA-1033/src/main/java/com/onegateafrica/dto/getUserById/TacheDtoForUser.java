package com.onegateafrica.dto.getUserById;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TacheDtoForUser {

    private long id;
    private String titre;
    private String description;
    private boolean progress;
}
