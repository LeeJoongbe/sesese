package com.example.demo.repository;

import com.example.demo.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {



    @Query(value = "select m.* from memo m  join user_entity  u on u.uno = m.uno where m.title like concat('%',:title,'%') " , nativeQuery = true)
    public List<Memo> selectTitle(String title);

    @Query("select m from Memo m  where m.title like concat('%',:title,'%')")
    public List<Memo> selectTitle2(String title);

    //쿼리메서드
    public List<Memo> findByTitleContains(String title);


    public Page<Memo> findByTitleContainsOrContentContaining(String keyword , String keyworda, Pageable pageable);







}
