package com.onegateafrica.services;

import com.onegateafrica.Entity.ProjectEntity;
import com.onegateafrica.Entity.TacheEntity;
import com.onegateafrica.Entity.UserEntity;
import com.onegateafrica.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ProjectService {

    public List<ProjectEntity> getAllProject();

    public ProjectEntity addProject(long id,ProjectEntity project);

    public ProjectEntity getProjectById(long id);

    public ProjectEntity updateproject(long id, ProjectEntity project);

    public ResponseEntity<ProjectEntity> deleteProjetctById(long id);

    public Optional<ProjectEntity> getprojectByIdForResponse(long id);

    List<UserDto> getAllUsersOfProject(long id);

    ProjectEntity addUsersToProject(long id, ArrayList<Long> users);

    List<TacheEntity> getTachesByUserIdAndProjectId(long userId,long projectId);

    List<UserEntity> getAllNonAffectedUserTopPoject(long projectId);

    void deleteUserFromProject(long projectId,long userId);

}
