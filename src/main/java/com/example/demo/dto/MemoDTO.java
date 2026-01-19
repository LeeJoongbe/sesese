package com.example.demo.dto;


import com.example.demo.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemoDTO {

    //글번호 , 제목 , 내용, 작성자 , 등록일자

    private Long mno;       //글번호

    private String title;   //제목 , not null, 20글이내

    private String content; //내용

    //이미지uuid포함이름
    private String imgName;
    //이미지 번호
    private Long imgno;
    //이미지이름
    private String orgName;

    private UserDTO userDTO;

    public MemoDTO setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
        return this;
    }
}
