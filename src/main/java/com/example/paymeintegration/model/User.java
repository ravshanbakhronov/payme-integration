package com.example.paymeintegration.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "USERS")
public class User implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_sequence")
    private Long id;

    @Column(name = "FIO")
    private String fio;

    @OneToMany(mappedBy = "user")
    private Set<Card> cards;
}
