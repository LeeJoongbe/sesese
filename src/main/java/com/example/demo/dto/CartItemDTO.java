package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CartItemDTO {

    @NotNull(message = "상품을 선택해주세요")
    private Long itemid;

    @Min(value = 1 , message = "최소수향은 1개입니다.")
    private Integer count;


}
