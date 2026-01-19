package com.example.demo.controller;

import com.example.demo.dto.MemoRegisterDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class SampleRestController {

    @GetMapping("/a")
    public void get() {

        log.info("a 요청 들어옴");
    }

    @GetMapping("/b")
    public ResponseEntity getb() {

        log.info("b 요청 들어옴");

        return new ResponseEntity<String>("홍길동", HttpStatus.OK);
    }

    @GetMapping("/c")
    public ResponseEntity<String> get(String name) {

        log.info("c요청 들어옴 :" + name);
        //정보처리
        name +=name;

        return new ResponseEntity<String>(name, HttpStatus.OK);

    }


    @GetMapping("/d")
    public ResponseEntity get(MemoRegisterDTO memoRegisterDTO) {

        log.info("d요청 들어옴 :" + memoRegisterDTO);
        //정보처리
        memoRegisterDTO.setContent("변경됨");

        return new ResponseEntity<MemoRegisterDTO>(memoRegisterDTO, HttpStatus.OK);

    }

    @PostMapping("/e")
    public void e(){
        log.info("포스트 e 진입");
    }

    @PostMapping("/f")
    public void f(@RequestBody MemoRegisterDTO memoRegisterDTO){
        log.info("포스트 f 진입" + memoRegisterDTO);
    }

    @PostMapping("/g")
    public ResponseEntity g(@RequestBody MemoRegisterDTO memoRegisterDTO){
        log.info("포스트 f 진입" + memoRegisterDTO);

        return new ResponseEntity<MemoRegisterDTO>(memoRegisterDTO, HttpStatus.OK);

    }


}
