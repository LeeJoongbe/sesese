package com.example.demo.repository;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class CartItemRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(CartItemRepositoryTest.class);
    @Autowired
    CartItemRepository cartItemRepository;

    @Test
    public void aa(){
        Pageable pageable = PageRequest.of( 0, 10 , Sort.by("id").descending());


        System.out.println(cartItemRepository.findByCartCno(6L , pageable));

    }

}