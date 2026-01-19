package com.example.demo.service;

import com.example.demo.dto.MemoDTO;
import com.example.demo.dto.MemoRegisterDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.ImgEntity;
import com.example.demo.entity.Memo;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.ImgRepository;
import com.example.demo.repository.MemoRepository;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;
    private final ImgService imgService;
    private final ImgRepository imgRepository;

    //모델매퍼 필드선언
    private ModelMapper modelMapper = new ModelMapper();

    //글등록 : 파라미터로 dto와 email을 받아서
    // 유니크한 필드인 email을 이용해서 유저엔티티를 받아와서
    // dto로 메모엔티티를 만들때 set하고 저장한다.
    // 저장후 반환x

    public void register(MemoRegisterDTO memoRegisterDTO , String email , List<MultipartFile>  multipartFiles){

        //글 등록자 찾기 :
        UserEntity userEntity = userRepository.findByEmail(email);

        Memo memo = modelMapper.map(memoRegisterDTO , Memo.class);

        memo.setUserEntity(userEntity);

        memo = memoRepository.save(memo);
        //사진저장
        for (MultipartFile multipartFile : multipartFiles) {
            imgService.imgRegister("memo" , memo.getMno() , multipartFile);
        }



    }

    //상세보기 : 읽기에서 쓸 dto만들기  memoDTO에서 사용할
    // userDTO도 만들어서 memoDTO에 표기

    public MemoDTO read(Long mno) {
        //pk로 글찾기
        Memo memo =
        memoRepository.findById(mno).orElseThrow(EntityNotFoundException::new);

        //찾은 글 entity 를 dto로 변환
        MemoDTO memoDTO = modelMapper.map(memo, MemoDTO.class);

        // 이미지 찾아오기

        List<ImgEntity> imgEntityList = imgRepository.findByMemoMno(mno);

        //DTO를 별도로 만들어서 이미지들을 저장해도 가능 하지만 우리는 1개의 게시글에
        // 1개만 이미지를 달고 있으니 일단 한개만
        if(!imgEntityList.isEmpty()) {   //이미지리스트가 비어있지 않다면
            memoDTO.setOrgName(imgEntityList.get(0).getOrgname());
            memoDTO.setImgName(imgEntityList.get(0).getImgname());
            memoDTO.setImgno(imgEntityList.get(0).getImgno());
        }


        //entity 안에 userEntity를 userDTO로 변환해서 memoDTO에 set하기
        memoDTO.setUserDTO(modelMapper.map(memo.getUserEntity() , UserDTO.class));

        return memoDTO;

    }


    //페이징처리를 추가한 모든데이터 + 검색까지 (keyword)하나 받기
    public List<MemoDTO>  list (Long page , String keyword) {

        //페이징처리를 위한 pageable
        Pageable pageable =
                PageRequest.of(page.intValue()-1, 10, Sort.by("mno").descending()  );

        //검색 keyword로 title와 content를 검색하는 like 사용한 쿼리문 만들기
        // pageable를 넣어주기 > page객체로 돌려받는 메서드 만들기

        //메서드 호출해서 데이터받기
        Page<Memo> memoPage = memoRepository.findByTitleContainsOrContentContaining(keyword,keyword,pageable);

        //Memo엔티티 > dto
//        List<MemoDTO> memoDTOList = memoPage.getContent().stream().map( a -> modelMapper.map(a, MemoDTO.class).setUserDTO(modelMapper.map(a, UserDTO.class)))
//                .collect(Collectors.toList());

        List<MemoDTO> memoDTOList = memoPage.getContent().stream().map( a -> {
            MemoDTO memoDTO = modelMapper.map( a, MemoDTO.class  );
            memoDTO.setUserDTO(   modelMapper.map(a.getUserEntity() , UserDTO.class));
            return memoDTO;
        }   ).collect(Collectors.toList());


        return memoDTOList;

    }

        //업데이트
    //변경할 DTO를 입력받아  기존 데이터를 찾아서 수정한다.
    // pk, title, content 를 가지고 있는 dto가 필요하다

    public void update(MemoDTO memoDTO){

        //글찾기 + 예외처리
        Memo memo =
        memoRepository.findById(memoDTO.getMno()).orElseThrow(EntityNotFoundException::new);

        //값을 수정합니다.
        memo.setTitle(   memoDTO.getTitle() );
        memo.setContent(   memoDTO.getContent()  );
        //변환후 저장
        memoRepository.save(memo);

        imgService.del(memoDTO.getImgno());


    }
    // 삭제할 pk
    public void del(MemoDTO memoDTO){
        //글찾기 + 예외처리
        Memo memo =
                memoRepository.findById(memoDTO.getMno()).orElseThrow(EntityNotFoundException::new);
        //삭제
        memoRepository.delete(memo);
    }
























}
