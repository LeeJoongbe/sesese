package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RequestPageDTO {

    private  Integer page = 1;
    private  Integer size = 10; //1페이지에 몇개씩 리스트갯수
    private  String  keyword="";
    private String sort;        //정렬조건




}
