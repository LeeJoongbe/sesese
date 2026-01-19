package com.example.demo.repository;

import com.example.demo.entity.Memo;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdersItemRepository extends JpaRepository<OrderItem, Long> {


    @Query("select oi from OrderItem oi where oi.orders.userEntity.email = :email")
    public Page<OrderItem> selectOrdersEmail(String email , Pageable pageable);


}
