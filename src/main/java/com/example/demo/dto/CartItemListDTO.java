package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemListDTO {

    private Long id;

    //상품명 ,가격, 상태(판매중), 수량
    private String iName;



    private Integer iPrice;

    private int count;  //장바구니에 담겨있는  아이템수량


}
