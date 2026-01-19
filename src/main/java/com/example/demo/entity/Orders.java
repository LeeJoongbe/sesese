package com.example.demo.entity;

import com.example.demo.dto.OrderItemDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ono;



    @ManyToOne
    @JoinColumn(name = "uno")
    private UserEntity userEntity;

    //양방향 설정
    @OneToMany(mappedBy = "orders" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<OrderItem> orderItemsList = new ArrayList<>();

}
