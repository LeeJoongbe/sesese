package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bno")
    private Long bno;

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "uno")
    private UserEntity userEntity;



}
