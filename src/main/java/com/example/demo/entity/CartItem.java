package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
//@ToString(exclude = {"cart"})
@Table(name = "cart_item" )
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_cno")
    private Long id;            //카트 기본키

    //장바구니 아이템들은 아이템을 참조

    @ManyToOne
    @JoinColumn(name = "ino")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "cno")
    private Cart cart;


    private int count;  //장바구니에 담기는 아이템수량



}
