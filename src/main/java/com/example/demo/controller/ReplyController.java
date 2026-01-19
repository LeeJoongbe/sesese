package com.example.demo.controller;

import com.example.demo.dto.ReplyDTO;
import com.example.demo.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@Log4j2
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {
    //restful방식

    private final ReplyService replyService;


    //댓글등록
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody ReplyDTO replyDTO , Principal principal){

        log.info("들어온 댓글 정보 : " + replyDTO );

        replyService.register(replyDTO , principal.getName());


        return new ResponseEntity<String>( "저장완료" , HttpStatus.OK);

    }

    //댓글목록
    @GetMapping("/list")
    public ResponseEntity list(Integer page, ReplyDTO replyDTO){



        if(page == null) {
            return new ResponseEntity<String>( "잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
        }
        List<ReplyDTO> list =
                replyService.list(page , replyDTO.getMno());

        return new ResponseEntity<List<ReplyDTO>>(list, HttpStatus.OK);   //잘 전송이 되었을때
    }

    @GetMapping("/read")
    public ResponseEntity read (Long rno){


        if(rno == null) {
            return new ResponseEntity<String>("게시글이 삭제되었거나 잘못된 접근입니다..", HttpStatus.BAD_REQUEST);
        }

        ReplyDTO replyDTO =
        replyService.read(rno);
        return new ResponseEntity<ReplyDTO>(replyDTO, HttpStatus.OK);   //잘 전송이 되었을때
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody ReplyDTO replyDTO, Principal principal){

        log.info(replyDTO);
        Long rno =
        replyService.update(replyDTO);

        return new ResponseEntity<Long>(rno , HttpStatus.OK);

    }

    @DeleteMapping("/del")
    public ResponseEntity del(@RequestBody ReplyDTO replyDTO, Principal principal){


        log.info(replyDTO);
        if(replyDTO.getRno() == null) {  // 로그인한 사용자의 글이 아니라면

            return new ResponseEntity<String >("이미 삭제된 게시글이거나 잘못된 접근입니다." , HttpStatus.BAD_REQUEST);
        }

        replyService.del(replyDTO.getRno());

        return new ResponseEntity<String >("삭제가 완료되었습니다." , HttpStatus.OK);

    }


}
