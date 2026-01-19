package com.example.demo.service;

import com.example.demo.dto.MemoDTO;
import com.example.demo.dto.MemoRegisterDTO;
import com.example.demo.repository.MemoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemoServiceTest {

    @Autowired
    MemoService memoService;

    @Test
    public void insertTest(){

        MemoRegisterDTO memoRegisterDTO = new MemoRegisterDTO();
        memoRegisterDTO.setTitle("이건 제목이고 서비스에서 등록한거야");
        memoRegisterDTO.setContent("이건 내용이고 서비스로 등록한거야");

        memoService.register( memoRegisterDTO, "hong@test.com");

    }



    @Test
    public void readTest(){


        MemoDTO memoDTO  = memoService.read(5L);

        System.out.println(memoDTO);
    }


}