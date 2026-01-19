package com.example.demo.controller;

import com.example.demo.constant.Role;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signupGet(UserDTO userDTO){

        //페이지 열기
        return "user/signup";


    }
    @PostMapping("/signup")
    public String signupGet(@Valid UserDTO userDTO , BindingResult bindingResult, RedirectAttributes redirectAttributes){

        log.info("회원가입 들어온 데이터 :  " + userDTO);

        //저장이나 기타 수정등을 하기 이전에 유효성 검사를 한다.
        if(bindingResult.hasErrors()){

            bindingResult.getAllErrors().forEach(a -> log.info(a));


            return "user/signup";
        }



        userDTO.setRole(Role.USER.name());
        try {
            userService.signup(userDTO);
        }catch (IllegalStateException e){
            e.printStackTrace();    //예외 발생 출력

            redirectAttributes.addFlashAttribute("msg" , e.getMessage());
            return "redirect:/user/signup";

        }
    
        //회원가입시 로그인이냐 메인이냐
        return "redirect:/memo/list";

    }
    
    //아이디 중복체크
    @ResponseBody
    @PostMapping("/emailc")
    public ResponseEntity signupGet(@RequestBody String email){


        log.info(email);

        //체크
        boolean cemail = userService.checkEmail(email);


        return new ResponseEntity<Boolean>(cemail , HttpStatus.OK);


    }


    //로그임 폼 전달 하는 아이
    @GetMapping("/login")
    public String login(){

        return "user/login";
    }

}
