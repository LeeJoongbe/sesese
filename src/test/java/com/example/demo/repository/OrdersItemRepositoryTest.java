package com.example.demo.repository;

import com.example.demo.entity.OrderItem;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrdersItemRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(OrdersItemRepositoryTest.class);
    @Autowired
    OrdersItemRepository ordersItemRepository;

    @Test
    public void selectOrdersEmailTest(){

        Pageable pageable = PageRequest.of(0 , 10, Sort.by("oino"));

        Page<OrderItem> orderItemPage =
        ordersItemRepository.selectOrdersEmail("sin@test.com" ,pageable );

        orderItemPage.getContent().forEach(  a    -> {
            System.out.println("주문아이템 : " + a.getItem());
            System.out.println("주문 번호 : " + a.getOrders().getOno());
            System.out.println("판매자 : " + a.getItem().getUserEntity());
            System.out.println("산사람 : " + a.getOrders().getUserEntity().getEmail());
            System.out.println("판매수량 : " + a.getOiCount());


        });

    }

}