package com.onegateafrica.dto;

import com.onegateafrica.Entity.DemandeEntity;

public class DemandeDto {
    DemandeEntity demande;
    String message;

    public DemandeDto(DemandeEntity demande, String message) {
        this.demande = demande;
        this.message = message;
    }
}
