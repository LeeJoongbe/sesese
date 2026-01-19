package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Memo {

    //글번호 , 제목 , 내용, 작성자 , 등록일자

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //memo_mno
    @Column(name = "mno")
    private Long mno;       //글번호

    @Column(name = "title" , nullable = false , length = 20)
    private String title;   //제목 , not null, 20글이내

    private String content; //내용


    @ManyToOne
    @JoinColumn(name = "uno")
    private UserEntity userEntity;




}
