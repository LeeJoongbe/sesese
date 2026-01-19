package com.example.demo.repository;

import com.example.demo.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {



    @Query("select r from Reply r where r.memo.mno = :mno")
    public Page<Reply> selectBymno (Long mno , Pageable pageable);

    //메서드 쿼리로 만든 기능

    public Page<Reply> findByMemoMno (Long mno , Pageable pageable);

}
