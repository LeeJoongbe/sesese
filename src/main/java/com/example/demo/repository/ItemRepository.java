package com.example.demo.repository;

import com.example.demo.entity.Item;
import com.example.demo.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {





    @Query("select i from  Item  i where i.itemStatus = 'SELL'")
    public Page<Item> goodSellList(Pageable pageable);





}
