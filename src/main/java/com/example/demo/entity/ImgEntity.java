package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImgEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgno;
    
    private String orgname;        //이미지 이름 본명
    private String imgname;        //이미지 이름 개명

    @ManyToOne
    @JoinColumn(name = "mno")
    private Memo memo;


    @ManyToOne
    @JoinColumn(name = "bno")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "ino")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "uno")
    private UserEntity userEntity;
}
