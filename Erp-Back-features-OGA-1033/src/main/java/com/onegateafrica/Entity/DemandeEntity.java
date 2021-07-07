package com.onegateafrica.Entity;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class DemandeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String type; // ( ATTESTATION - FICHE_PAIE - CONGE - AVANCE - AUTRE )
    private String info; // par le system
    private int status; // ( 0 => en attente - 1 => validé - 2 => réfusé )
    private long idEmploye;
    private String raison; // par l'mployé
    private long dateCreation;
    private String commentaireEmploye; // par l'employé
    private long idGrh;
    private String commentaireGrh; // par le grh
//    private String commentaire;
    private long dateReponse;
    private Date dateDebut;
    private Date dateFin;

    /** user */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserEntity userEntity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(long idEmploye) {
        this.idEmploye = idEmploye;
    }

    public String getRaison() {
        return raison;
    }

    public void setRaison(String raison) {
        this.raison = raison;
    }

    public long getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(long dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getCommentaireEmploye() {
        return commentaireEmploye;
    }

    public void setCommentaireEmploye(String commentaireEmploye) {
        this.commentaireEmploye = commentaireEmploye;
    }

    public long getIdGrh() {
        return idGrh;
    }

    public void setIdGrh(long idGrh) {
        this.idGrh = idGrh;
    }

    public String getCommentaireGrh() {
        return commentaireGrh;
    }

    public void setCommentaireGrh(String commentaireGrh) {
        this.commentaireGrh = commentaireGrh;
    }

    public long getDateReponse() {
        return dateReponse;
    }

    public void setDateReponse(long dateReponse) {
        this.dateReponse = dateReponse;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

//    public String getCommentaire() {
//        return commentaire;
//    }
//
//    public void setCommenaire(String commentaire) {
//        this.commentaire = commentaire;
//    }
}
