package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;
    
    //댓글은 내용 작성자 , 어떤게시물을 참조하는가

    @Column(length = 50)
    private String content;

    @ManyToOne
    @JoinColumn(name = "uno")
    private UserEntity userEntity;


    @ManyToOne
    @JoinColumn(name = "memo")
    private Memo memo;

}
