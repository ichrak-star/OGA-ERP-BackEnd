package com.onegateafrica.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class TacheEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id", unique = true, nullable = false)
        private long id;
        @Column(name = "titre", nullable = false)
        private String titre;
        @Column(name = "description", nullable = false)
        private String description;
        @Column(name = "progress", nullable = false)
        private boolean progress;
        private String DateDebut;
        private String DateFin;
        private String Dificulte;


        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "project_id", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)

        @JsonIgnore
        private ProjectEntity project;

        @ManyToMany(fetch = FetchType.LAZY,
                cascade = {
                        CascadeType.PERSIST,
                        CascadeType.MERGE,
                })
        @JoinTable(name = "user_tache",
                joinColumns = { @JoinColumn(name = "tache_id") },
                inverseJoinColumns = { @JoinColumn(name = "user_id") })



        @JsonIgnore
        private List<UserEntity> users = new ArrayList<UserEntity>();
    }


