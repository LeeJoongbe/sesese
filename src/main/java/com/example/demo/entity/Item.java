package com.example.demo.entity;

import com.example.demo.constant.ItemStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ino;

    private String iName;
    private String iDetail;
    private Integer iCount;
    private Integer iPrice;

    private Long salesQuantity = 0L;  // 판매수량

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;  //판매중 , 품절


    @ManyToOne
    @JoinColumn(name = "uno")
    private UserEntity userEntity;



    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImgEntity> imgEntityList;


}
