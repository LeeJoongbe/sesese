package com.example.demo.repository;

import com.example.demo.entity.ImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImgRepository extends JpaRepository<ImgEntity, Long> {

    public List<ImgEntity> findByMemoMno (Long mno);


}
