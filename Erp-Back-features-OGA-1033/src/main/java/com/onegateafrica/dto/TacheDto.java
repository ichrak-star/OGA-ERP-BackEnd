package com.onegateafrica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TacheDto {
    private long id;
    private String titre;
    private String description;
    private boolean progress;
    private List<UserDto> users;
}
