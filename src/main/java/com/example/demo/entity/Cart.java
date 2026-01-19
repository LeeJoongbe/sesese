package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data       //getter setter toString
@ToString(exclude = {"cartItemList"})
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;

    //회원참조 : 회원 엔티티와 일대일로 매핑
    @OneToOne
    @JoinColumn(name = "uno")
    private UserEntity userEntity;



    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItemList;


    //아이템목록 만들어주는 메서드

    public Cart setCartItemList(List<CartItem> itemList){

        this.cartItemList = itemList;

        return this;

    }
    public List<CartItem> addCartItemList( CartItem cartItem) {

        if(cartItemList == null || cartItemList.isEmpty()) {
            this.cartItemList = new ArrayList<>();
        }
        this.cartItemList.add(cartItem);

        return cartItemList;

    }






}
