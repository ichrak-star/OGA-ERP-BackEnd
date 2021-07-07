package com.onegateafrica.repository;

import com.onegateafrica.Entity.AppelDoffreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppelDoffreRepository extends JpaRepository<AppelDoffreEntity,Long> {
}
