package com.example.demo.controller;

import com.example.demo.dto.ItemDTO;
import com.example.demo.entity.Item;
import com.example.demo.service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/admin/item") //관리자용입니다. 이이하
public class ItemController {


    //서비스 넣기

    private final ItemService itemService;

    //등록 폼 돌려주기
    @GetMapping("/register")
    public String registerGet(ItemDTO itemDTO){
        
        //유효성검사를 위해서 파라미터에 dto도 넣어줄 예정

        return "item/register";
    }

    @PostMapping("/register")
    public String register(@Valid ItemDTO itemDTO, BindingResult bindingResult  , Principal principal , RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()){
            //유효성검사
            log.info(bindingResult.getAllErrors());

            return "item/register";
        }


        log.info("상품등록으로 들어온 dto : " + itemDTO);

        //저장 서비스 : 값이 등록이 된상태에서 pk값을 돌려받아 리다이렉트로 전달해준다.

        Long ino = itemService.register(itemDTO, principal.getName());

        redirectAttributes.addAttribute("ino" , ino);
        //상세보기로 넘겨주면서 파라미터를 넘기기위해서 리다이렉트어트리뷰트 적용시키기 플러시
        return "redirect:/item/read";
    }

    //상세보기

    @GetMapping("/read")
    public String read(ItemDTO itemDTO  , Model model   , Principal principal, RedirectAttributes redirectAttributes){

        if(itemDTO.getIno() == null) {
            return "redirect:/item/list";
        }


        log.info("item 상세보기 ino: " + itemDTO.getIno());
        //서비스 호출해서 데이터 받기
        try {
            itemDTO = itemService.read(itemDTO.getIno()   , principal.getName()  ); //서비스 연결할 예정
            model.addAttribute("itemDTO", itemDTO);
            return "item/read";
        }catch (EntityNotFoundException e) {
            //내글이 내 아이템이 내가 등록한 아이템 아닐경우
            log.info("상품번호 잘못됨");
            redirectAttributes.addFlashAttribute("msg" , "잘못된 접근입니다.");
            return "redirect:/item/list";
        }catch (IllegalArgumentException e) {
            //내글이 내 아이템이 내가 등록한 아이템 아닐경우
            log.info("내상품 아님");
            redirectAttributes.addFlashAttribute("msg" , "잘못된 접근입니다.");
            return "redirect:/item/list";
        }

    }


    @GetMapping("/update")
    public String updateGet(ItemDTO itemDTO  , Model model , Principal principal, RedirectAttributes redirectAttributes){

        if(itemDTO.getIno() == null) {
            return "redirect:/item/list";
        }


        log.info("item 상세보기 ino: " + itemDTO.getIno());
        //서비스 호출해서 데이터 받기
        try {
            itemDTO = itemService.read(itemDTO.getIno() , principal.getName()); //서비스 연결할 예정
            model.addAttribute("itemDTO", itemDTO);
            return "item/update";
        }catch (EntityNotFoundException e) {
            //내글이 내 아이템이 내가 등록한 아이템 아닐경우
            log.info("상품번호 잘못됨");
            redirectAttributes.addFlashAttribute("msg" , "잘못된 접근입니다.");
            return "redirect:/item/list";
        }catch (IllegalArgumentException e) {
            //내글이 내 아이템이 내가 등록한 아이템 아닐경우
            log.info("내상품 아님");
            redirectAttributes.addFlashAttribute("msg" , "잘못된 접근입니다.");
            return "redirect:/item/list";
        }

    }

    @PostMapping("/update")
    public String updatePost(@Valid ItemDTO itemDTO, BindingResult bindingResult  , Model model,
                             RedirectAttributes redirectAttributes, Principal principal ){

        if(bindingResult.hasErrors()) {
            log.info(bindingResult.getAllErrors());

            return "item/update";
        }
        
        //수정작업 서비스 호출
        try {
            Long ino = itemService.update(itemDTO, principal.getName());
        }catch (EntityNotFoundException e) {
            //내글이 내 아이템이 내가 등록한 아이템 아닐경우
            log.info("상품번호 잘못됨");
            redirectAttributes.addFlashAttribute("msg" , "잘못된 접근입니다.");
            return "redirect:/item/list";
        }catch (IllegalArgumentException e) {
            //내글이 내 아이템이 내가 등록한 아이템 아닐경우
            log.info("내상품 아님");
            redirectAttributes.addFlashAttribute("msg" , "잘못된 접근입니다.");
            return "redirect:/item/list";
        }


        redirectAttributes.addAttribute("ino" , itemDTO.getIno());
        //상세보기로 넘겨주면서 파라미터를 넘기기위해서 리다이렉트어트리뷰트 적용시키기 플러시
        return "redirect:/item/read";

    }

    @PostMapping("/del")
    public String del(ItemDTO itemDTO  , Model model ,RedirectAttributes redirectAttributes , Principal principal){

        if(itemDTO.getIno() == null) {
            return "redirect:/item/list";
        }


        log.info("item 상세보기 ino: " + itemDTO.getIno());
        //서비스 호출해서 데이터 삭제

        try {

            itemService.del(itemDTO, principal.getName());

        }catch (EntityNotFoundException e) {
            //내글이 내 아이템이 내가 등록한 아이템 아닐경우
            log.info("상품번호 잘못됨");
            redirectAttributes.addFlashAttribute("msg" , "잘못된 접근입니다.");
            return "redirect:/item/list";
        }catch (IllegalArgumentException e) {
            //내글이 내 아이템이 내가 등록한 아이템 아닐경우
            log.info("내상품 아님");
            redirectAttributes.addFlashAttribute("msg" , "잘못된 접근입니다.");
            return "redirect:/item/list";
        }


        redirectAttributes.addFlashAttribute("msg" , "삭제되었습니다.");
        return "redirect:/item/list";

    }


}
