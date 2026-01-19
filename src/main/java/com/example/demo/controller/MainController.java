package com.example.demo.controller;

import com.example.demo.dto.ItemDTO;
import com.example.demo.dto.RequestPageDTO;
import com.example.demo.dto.ResponsePageDTO;
import com.example.demo.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MainController {

    private final ItemService itemService;

    @GetMapping("/")
    public String mainPage(Model model , RequestPageDTO requestPageDTO){


        ResponsePageDTO<ItemDTO> responsePageDTO = itemService.listWhere(requestPageDTO);

        model.addAttribute("responsePageDTO" , responsePageDTO);


        return "main/main";

    }

}
