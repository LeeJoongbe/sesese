package com.example.demo.dto;

import com.example.demo.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersDTO {

    private Long ono;

    private UserDTO userDTO;

    private List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
}
