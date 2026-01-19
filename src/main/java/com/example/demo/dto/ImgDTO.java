package com.example.demo.dto;

import com.example.demo.entity.Board;
import com.example.demo.entity.Item;
import com.example.demo.entity.Memo;
import com.example.demo.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImgDTO {

    private Long imgno;
    
    private String orgname;        //이미지 이름 본명
    private String imgname;        //이미지 이름 개명

    private MemoDTO memoDTO;

    private ItemDTO itemDTO;

    private UserDTO userDTO;
}
