package com.example.demo.dto;

import com.example.demo.entity.Memo;
import com.example.demo.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {
    private Long rno;
    private Long mno;
    private String content;
    private UserDTO userDTO;
    private MemoDTO memoDTO;

}
