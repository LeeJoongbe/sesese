package com.example.demo.dto;


import com.example.demo.entity.UserEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemoRegisterDTO {

    private String title;

    private String content;



}
