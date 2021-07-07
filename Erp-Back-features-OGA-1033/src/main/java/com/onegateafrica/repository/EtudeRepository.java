package com.onegateafrica.repository;

import com.onegateafrica.Entity.EtudeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtudeRepository extends JpaRepository<EtudeEntity, Long> {
}
