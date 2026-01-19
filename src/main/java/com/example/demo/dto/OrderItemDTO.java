package com.example.demo.dto;

import com.example.demo.entity.Item;
import com.example.demo.entity.Orders;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {

    private Long oino;

    //구매수량
    private Integer oiCount;


    private OrdersDTO ordersDTO;



    private ItemDTO itemDTO;

}
