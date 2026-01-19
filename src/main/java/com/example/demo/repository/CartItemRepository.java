package com.example.demo.repository;

import com.example.demo.entity.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem , Long> {


    public Page<CartItem> findByCartCno(Long cno , Pageable pageable);

}
