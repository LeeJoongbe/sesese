package com.example.demo.controller;

import com.example.demo.dto.MemoDTO;
import com.example.demo.dto.MemoRegisterDTO;
import com.example.demo.dto.ReplyDTO;
import com.example.demo.dto.RequestPageDTO;
import com.example.demo.service.MemoService;
import com.example.demo.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/memo")
@Log4j2
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;
    private final ReplyService replyService;


    @GetMapping("/register")
    public String register(){
        log.info("메모 글등록 get");
        return "memo/register";
    }

    @PostMapping("/register")
    public String register(MemoRegisterDTO memoRegisterDTO , Principal principal, List<MultipartFile> imgs){
        log.info("메모 글등록 post : " + memoRegisterDTO );
        log.info("이미지가 있니?"+ imgs);
        imgs.forEach(a -> log.info(a));
//        if( !img.isEmpty() ) {
//            log.info("이미지 제목 " + img.getName());
//            log.info("이미지" + img.getOriginalFilename());
//        }

        memoService.register(memoRegisterDTO, principal.getName() , imgs);


        return "redirect:/memo/list";
    }

    @GetMapping("/list")
    public String list(RequestPageDTO requestPageDTO, Model model){
        List<MemoDTO> memoDTOList =
        memoService.list(requestPageDTO.getPage().longValue(), requestPageDTO.getKeyword());

        //전송할 데이터
        model.addAttribute("memoDTOList", memoDTOList);

        return "memo/list";

    }
    
    //읽기

    @GetMapping("/read")
    public String read(Long mno , Model model , RedirectAttributes redirectAttributes) {

        if(mno == null) {
            redirectAttributes
                    .addFlashAttribute("msg" , "삭제된 글번호이거나 잘못된 접근입니다.");
            return "redirect:/memo/list";
        }


        model.addAttribute("memoDTO" , memoService.read(mno));

        return "memo/read";

    }

    @GetMapping("/update")
    public String update(Long mno , Model model , RedirectAttributes redirectAttributes) {

        if(mno == null) {
            redirectAttributes
                    .addFlashAttribute("msg" , "삭제된 글번호이거나 잘못된 접근입니다.");
            return "redirect:/memo/list";
        }

        model.addAttribute("memoDTO" , memoService.read(mno));

        return "memo/update";

    }

    @PostMapping("/update")
    public String update(MemoDTO memoDTO , RedirectAttributes redirectAttributes  ) {

        log.info("업데이트 포스트로 들어오는 값 : " + memoDTO);

        memoService.update(memoDTO);

        redirectAttributes.addAttribute("mno" , memoDTO.getMno());

        return "redirect:/memo/read";
    }

    @PostMapping("/del")
    public String del(MemoDTO memoDTO){

        memoService.del(memoDTO);

        return "redirect:/memo/list";
    }





}
