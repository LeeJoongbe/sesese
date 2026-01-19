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
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oino;

    //구매수량
    private Integer oiCount;


    @ManyToOne
    @JoinColumn(name = "ono")
    private Orders orders;



    @ManyToOne
    @JoinColumn(name = "ino")
    private Item item;

}
