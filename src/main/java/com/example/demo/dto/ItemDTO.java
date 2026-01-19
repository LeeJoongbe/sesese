package com.example.demo.dto;

import com.example.demo.constant.ItemStatus;
import com.example.demo.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO {
    private Long ino;

    @NotBlank
    @Size(min = 2, max = 20)
    private String iName;

    @NotBlank
    @Size(min = 2, max = 200)
    private String iDetail;

    @PositiveOrZero
    @Range(min = 1, message = "최소수량은 1개부터입니다.")
    private Integer iCount;



    @NotNull(message = "상품 가격을 입력해주세요")
    @PositiveOrZero
    @Range(min = 10, message = "최소가격은 10원부터입니다.")
    private Integer iPrice;

    private ItemStatus itemStatus;  //판매중 , 품절

    private UserDTO userDTO;

    List<ImgDTO> imgDTOList = new ArrayList<>();



}
