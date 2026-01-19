package com.example.demo.service;


import com.example.demo.constant.ItemStatus;
import com.example.demo.dto.ImgDTO;
import com.example.demo.dto.ItemDTO;
import com.example.demo.dto.RequestPageDTO;
import com.example.demo.dto.ResponsePageDTO;
import com.example.demo.entity.ImgEntity;
import com.example.demo.entity.Item;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.ItemRepository;
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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class ItemService {


    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    private ModelMapper modelMapper = new ModelMapper();


    //등록

    public Long register(ItemDTO itemDTO , String email){

        //판매상태는 무조건 처음은 품절상태 시작
        itemDTO.setItemStatus(ItemStatus.SOLDOUT);

        //상품을 저장하기위해서 누가 판매하고 있는가? 유저정보를 가져와야한다.
        //email
        //부모값
        UserEntity userEntity =
        userRepository.findByEmail(email);

        //ItemDTO를 엔티티로 변형
        Item item = modelMapper.map(itemDTO , Item.class);

        //부모값 세팅
        item.setUserEntity( userEntity );

        item = itemRepository.save(item);

        return item.getIno();

    }

    //pk번호를 받아서 ItemDTO객체로 반환하는 기능을 가진 메서드
    public ItemDTO read(Long ino , String email){
        Item item =
        itemRepository.findById(ino)
                .orElseThrow(EntityNotFoundException::new);
        //dto변환
//        ItemDTO itemDTO  = modelMapper.map(item, ItemDTO.class);
//        return itemDTO;

        if(!item.getUserEntity().getEmail().equals(email)) {
            //상품의 등록자와 현재 로그인한 대상 다르다면
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }


        return modelMapper.map(item, ItemDTO.class);

    }
    public ItemDTO read(Long ino ){
        Item item =
                itemRepository.findById(ino)
                        .orElseThrow(EntityNotFoundException::new);
        //dto변환
//        ItemDTO itemDTO  = modelMapper.map(item, ItemDTO.class);
//        return itemDTO;


        return modelMapper.map(item, ItemDTO.class);

    }

    //상품의 pk번호를 받아서 , 데이터를 찾아와서
    // dto에 변경할 내용들을 적용해서 수정한다.
    // 단!! 아이템의 등록자와 현재 로그인한 사람이 같다면
    public Long update(ItemDTO itemDTO , String email) {


        Item item =
        itemRepository.findById(itemDTO.getIno())
                .orElseThrow(EntityNotFoundException::new);

        if(!item.getUserEntity().getEmail().equals(email)) {
            //상품의 등록자와 현재 로그인한 대상 다르다면
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }

        //수정작업
        item.setICount(itemDTO.getICount());
        item.setIPrice(itemDTO.getIPrice());
        item.setIName(itemDTO.getIName());
        item.setIDetail(itemDTO.getIDetail());

        return item.getIno();
    }


    public void del (ItemDTO itemDTO , String email){
        Item item =
                itemRepository.findById(itemDTO.getIno())
                        .orElseThrow(EntityNotFoundException::new);

        if(!item.getUserEntity().getEmail().equals(email)) {
            //상품의 등록자와 현재 로그인한 대상 다르다면
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }

        itemRepository.delete(item);
    }




    //상품페이지 + 정렬조건까지 + 몇개씩 + 페이지 + 검색조건
    public ResponsePageDTO listWhere(RequestPageDTO requestPageDTO  ){


        Pageable pageable = null;

        if(requestPageDTO.getSort() != null && requestPageDTO.getSort().equals("최신순")){
            pageable = PageRequest.of(requestPageDTO.getPage(),  requestPageDTO.getSize() , Sort.by(requestPageDTO.getSort()).descending());
        }

        if(requestPageDTO.getKeyword() != null || requestPageDTO.getKeyword().length() > 1){
            //검색어에 대한 쿼리문 where에 작성
        }

        // 판매량 많은 순으로 쿼리문 가져오기
        pageable = PageRequest
                .of(0, 10, Sort.by("salesQuantity").descending());     //컬럼? 필드?

        Page<Item> itemPage =
        itemRepository.goodSellList(pageable);

        List<ItemDTO> itemDTOList = itemPage.getContent().stream().map(a ->      {

            List<ImgDTO> imgDTOList = Arrays.asList( modelMapper.map(  a.getImgEntityList() , ImgDTO[].class) );

             ItemDTO itemDTO =   modelMapper.map(a, ItemDTO.class);

             itemDTO.setImgDTOList(imgDTOList);
             return itemDTO;

                })
                .collect(Collectors.toList());

        ResponsePageDTO<ItemDTO> responsePageDTO =
                new ResponsePageDTO<>(itemDTOList , requestPageDTO.getPage() ,(int) itemPage.getTotalElements());


        return responsePageDTO;



    }
}
