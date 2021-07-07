package com.onegateafrica.repository;


import com.onegateafrica.Entity.DemandeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface DemandeRepository extends JpaRepository<DemandeEntity,Long> {
}
