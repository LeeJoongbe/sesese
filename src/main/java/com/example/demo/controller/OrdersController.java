package com.example.demo.controller;

import com.example.demo.dto.OrderItemDTO;
import com.example.demo.dto.OrdersDTO;
import com.example.demo.dto.RequestPageDTO;
import com.example.demo.dto.ResponsePageDTO;
import com.example.demo.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Arrays;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService ordersService;


    @PostMapping("/register")
    public String register(OrderItemDTO orderItemDTO , Principal principal
            , RedirectAttributes redirectAttributes){
        //상품번호와 수량을 받아서
        //넘겨준다.
        log.info("들어온 데이터 : " + orderItemDTO);

        String str = ordersService.register(orderItemDTO , principal.getName());


        redirectAttributes.addFlashAttribute("msg" , str);

        return "redirect:/orders/list";

    }

    @GetMapping("/list")
    public  String list(RequestPageDTO requestPageDTO , Principal principal , Model model){

        log.info(requestPageDTO);

        //서비스   email을 이용해서 주문목록과 그에 매칭되는 주문아이템목록

        ResponsePageDTO<OrdersDTO> responsePageDTO =
        ordersService.list(requestPageDTO , principal.getName());

        model.addAttribute("responsePageDTO" ,responsePageDTO);


        return "orders/list";
    }

    @PostMapping("/orders")
    public ResponseEntity orders(@RequestBody Map<String , Long[]> aa , Principal principal){

        log.info("받은 데이터 : " + Arrays.toString(aa.get("nums")));

        try {

            ordersService.orders(aa.get("nums") , principal.getName());

            return new ResponseEntity<String >("참잘했어요" , HttpStatus.OK);
        }catch (Exception e) {
            if(e.getMessage().equals("상품미판매")) {
                return new ResponseEntity<String >("상품미판매" , HttpStatus.BAD_REQUEST);
            }
        }


        return null;


    }



}
