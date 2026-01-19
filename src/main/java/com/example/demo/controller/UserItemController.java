package com.example.demo.controller;

import com.example.demo.dto.ItemDTO;
import com.example.demo.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
@Log4j2
public class UserItemController {

    private final ItemService itemService;

    //사용자 상세 페이지
    
    
    
    //사용자 아이템 주문페이지
    @GetMapping("/read")
    public String read(Long ino, Model model, RedirectAttributes redirectAttributes){
        log.info("컨트롤러에 입력된 ino : " + ino);

        ItemDTO itemDTO = itemService.read(ino);    //상품번호를 입력받아 데이터 반환
                                                    //로그인한사용자 여부는 필요없다

        log.info("브라우저로 넘어가는 데이터 : " + itemDTO);
        model.addAttribute("itemDTO", itemDTO);

        return "item/product";      //주문페이지
    }













}
