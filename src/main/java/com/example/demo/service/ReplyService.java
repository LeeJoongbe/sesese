package com.example.demo.service;

import com.example.demo.dto.ReplyDTO;

import java.util.List;

public interface ReplyService {

    //등록
    public void register(ReplyDTO replyDTO , String email);
    
    // 댓글 목록
    public List<ReplyDTO> list(int page, Long mno);

    // 상세보기
    public ReplyDTO read(Long rno);


    //댓글 수정
    public Long update(ReplyDTO replyDTO);
    
    //댓글 삭제
    public void del(Long rno);

}
