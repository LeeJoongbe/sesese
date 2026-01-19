package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.CartItemService;
import com.example.demo.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final CartItemService cartItemService;


    @PostMapping("/register")
    public String register(
            @Valid  CartItemDTOS cartItemDTOS, BindingResult bindingResult ,
            Principal principal, RedirectAttributes redirectAttributes){

        log.info("들어온값 : " + cartItemDTOS);

        if(bindingResult.hasErrors()) {
            log.info(bindingResult.getAllErrors());

//            bindingResult.getAllErrors(); 리다일렉트로 넘기기

            redirectAttributes.addFlashAttribute("msg" , "수량 등 입력값이 올바르지 않습니다.");

            return "redirect:/item/list";   //기존 아이템list는 url item/list -> admin/item/list
        }

        try {
            cartService.register(principal.getName() , cartItemDTOS.getCartItemDTOList());
        }catch (EntityNotFoundException e){
            redirectAttributes.addFlashAttribute("msg" , "수량 등 입력값이 올바르지 않습니다.");
            return "redirect:/item/list";   //기존 아이템list는 url item/list -> admin/item/list

        }catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("msg" , e.getMessage());
            return "redirect:/item/list";   //기존 아이템list는 url item/list -> admin/item/list
        }

        return "redirect:/cart/list";   //성공시 장바구니 목록

    }


    //장바구니 리스트: 장바구니 리스트 장바구니를 통해서 찾는다 번호
    // 장바구니 번호는 user와 1:1 맵핑되어있다
    // email로 찾으면 된다.
    @GetMapping("/list")
    public String list(RequestPageDTO requestPageDTO , Model model , Principal principal
            ,RedirectAttributes redirectAttributes){

        log.info(principal);
        //페이징처리는 하자!!


        try {
            ResponsePageDTO<CartItemListDTO> responsePageDTO = cartService.list(requestPageDTO,principal.getName());

            model.addAttribute("responsePageDTO" , responsePageDTO);
            return "cart/list";

        }catch (IllegalArgumentException e){

            redirectAttributes.addFlashAttribute("msg" , e.getMessage());

            return "redirect:/item/list";       //상품리스트 아직 만들지 않았다.
        }



    }


    @PutMapping("/updateCount")
    public ResponseEntity updateCountPut(@RequestBody  CartItemDTO cartItemDTO){
        log.info("수량변경 restful 방식" + cartItemDTO);


        try {
            cartItemService.updateCount(cartItemDTO);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<String>("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
        }



        return new ResponseEntity<String>("수정완료", HttpStatus.OK);
    }

    @DeleteMapping("/del")
    public ResponseEntity del(@RequestBody Map<String , Long[]>  a , Principal principal){

        log.info("넘어온 값 : " + Arrays.toString(a.get("nums")));
        //삭제

        try {

            cartItemService.del(a.get("nums")   , principal.getName());

        }catch (EntityNotFoundException e) {


            return new ResponseEntity<String>("삭제불가?" , HttpStatus.BAD_REQUEST);

        }catch (Exception e){
            if(e.getMessage().equals("너님꺼 아님")) {
                //로그인을 해제, 회원 탈퇴

                return new ResponseEntity<String>("삭제불가?" , HttpStatus.BAD_REQUEST);

            }
        }


        return new ResponseEntity<String>("삭제가 되었을껄?" , HttpStatus.OK);

    }



}
