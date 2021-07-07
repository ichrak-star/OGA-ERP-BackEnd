package com.onegateafrica.repository;

import com.onegateafrica.Entity.QualityEntity;
import com.onegateafrica.Entity.TacheEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TacheRepository extends JpaRepository<TacheEntity,Long> {
}
