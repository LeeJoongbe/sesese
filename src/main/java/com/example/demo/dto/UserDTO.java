package com.example.demo.dto;


import com.example.demo.constant.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long uno;


    @NotBlank(message = "님 이멜을 왜 비워둬?")
    @Email(message = "님 이멜을 써")
    @Size(max = 20)
    private String email;

    @Size(min = 8 , max = 20 , message = "8~20사이 써")
    private String password;

    @NotBlank(message = "이름이 왜 없어?")
    @Size(min = 2, max=10 , message = "2~10글자")
    private String name;

    @Size(max = 13, min=10)
    private String tel;

    private String role;

}
