package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.util.FileUpload;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ImgService {

    private final BoardRepository boardRepository;
    private final MemoRepository memoRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ImgRepository imgRepository;
    private final FileUpload fileUpload;

    
    
    //사진등록 : 메모인지, 유저인지, 아이템인지, 게시판인지를 입력받고
    // 해당 참조할 번호를 입력받고 , 받은 유형에 따라 번호로 findById로 찾아서
    // 내가 등록할 이미지에 set한다 참조한다.

    public  void imgRegister(String type  , Long id , MultipartFile multipartFile){

        ImgEntity imgEntity = new ImgEntity();

        if(type.equals("board")) {

            //board 이미지
            Board board = boardRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            imgEntity.setBoard(board);
        }else if(type.equals("memo")){
            //memo 이미지
            Memo memo = memoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            imgEntity.setMemo(memo);
        }else if(type.equals("item")){
            //item 이미지
            Item item = itemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            imgEntity.setItem(item);
        }else if(type.equals("user")){
            //user 이미지
            UserEntity userEntity = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            imgEntity.setUserEntity(userEntity);
        }

        //물리적인 파일저장

        try {
            String newFileName =  fileUpload.save(multipartFile);
            String orgName = newFileName.substring( newFileName.lastIndexOf('_') +1 );
            //uuid를 제외하고 파일명만 가져온 문자열

            imgEntity.setOrgname(orgName);     //이미지 이름
            imgEntity.setImgname(newFileName);     //이미지의 경로를 찾기위한 이름

        }catch (IOException e) {
            e.printStackTrace();
            log.info("사진저장안됨");
            log.info("사진저장안됨");
            log.info("사진저장안됨");
            log.info("사진저장안됨");
            log.info("사진저장안됨");
        }

        imgRepository.save(imgEntity);      //디비에 저장




    }


    public void del(Long imgno) {


        if(imgno == null){
            log.info("삭제할 이미지가 없습니다.");
        }else {
            //디비에서 이미지 삭제
            ImgEntity imgEntity =
                    imgRepository.findById(imgno).orElseThrow(EntityNotFoundException::new);

            //물리적인 파일삭제
            fileUpload.removefile( imgEntity.getImgname() );


            imgRepository.delete(imgEntity);
        }

    }

}
