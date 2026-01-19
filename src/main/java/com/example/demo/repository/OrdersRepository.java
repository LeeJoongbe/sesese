package com.example.demo.repository;

import com.example.demo.entity.Memo;
import com.example.demo.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {



    @Query("select o from Orders o where o.userEntity.email =:email")
    public Page<Orders> selectByEmail(String email , Pageable pageable);


}
