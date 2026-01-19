package com.example.demo.entity;


import com.example.demo.constant.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uno")
    private Long uno;

    @Column(unique = true , nullable = false, length = 20)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;

    private String tel;


    //권한 구분
    @Enumerated(EnumType.STRING)
    private Role role;

}
