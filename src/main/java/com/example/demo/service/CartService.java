package com.example.demo.service;

import com.example.demo.dto.CartItemDTO;
import com.example.demo.dto.CartItemListDTO;
import com.example.demo.dto.RequestPageDTO;
import com.example.demo.dto.ResponsePageDTO;
import com.example.demo.entity.Cart;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.Item;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CartRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CartService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    private ModelMapper modelMapper = new ModelMapper();


    //사용자가 로그인(컨트롤러)후
    //이메일을 이용해서 장바구니를 만든다.
    //아이템을 참조한 카트아이템들을 장바구니에 담는다.
    public void register(String email, List<CartItemDTO> cartItemDTOList ){


        //사용자 이메일로 검색 없으면 예외처리
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null) {
            throw new IllegalArgumentException("잘못된 접근입니다.");//이메일 사용자를 못찾음
        }

        //장바구니
        Cart cart = cartRepository.findByUserEntityEmail(email);    //로그인사용자의 이메일로 장바구니 불러오기

        //장바구니가 없다면
        if(cart == null) {
            cart = new Cart();
            cart.setUserEntity(userEntity);     //로그인한 사용자 참조
        }
        //장바구니에 아이템들을 담을 목록들
        log.info("서비스에 들어온 " + cartItemDTOList);

        //장바구니 아이템들 찾아오기
        for(CartItemDTO cartItemDTO : cartItemDTOList){

            log.info("서비스에 들어온 " + cartItemDTO);
            //참조 대상인 아이템들 가져오기 : 없다면 예외처리
            Item item = itemRepository.findById(cartItemDTO.getItemid()).orElseThrow(EntityNotFoundException::new);
            //장바구니 아이템
            CartItem cartItem = new CartItem();
            cartItem.setItem(item);
            cartItem.setCart(cart);
            cartItem.setCount(cartItemDTO.getCount());        //수량

            cart.addCartItemList(cartItem);

        }

        cartRepository.save(cart);


    }


    public ResponsePageDTO list(RequestPageDTO requestPageDTO, String email){

        //이메일로 카트엔티티 가져오기

        Cart cart =
        cartRepository.findByUserEntityEmail(email);

        if(cart == null) {
            throw new IllegalArgumentException("장바구니에 담긴 상품이 없습니다.");
        }

        log.info(cart);

        Pageable pageable = PageRequest.of(requestPageDTO.getPage()-1 , 10 , Sort.by("id").descending());


        log.info(pageable);
        Page<CartItem> cartItemPage = cartItemRepository.findByCartCno(cart.getCno() , pageable);

        log.info(cartItemPage);

        cartItemPage.getContent().forEach( cartItem -> log.info(cartItem) );
        //list화 시키고

        List<CartItem>  cartItemList = cartItemPage.getContent();

        //dto화 시키고
        List<CartItemListDTO> cartItemDTOList =
                cartItemList.stream().map(a -> {

                    return CartItemListDTO.builder()
                            .iName(a.getItem().getIName())
                            .iPrice(a.getItem().getIPrice())
                            .count(a.getCount())
                            .id(a.getId())
                            .build();
                }).collect(Collectors.toList());





        cartItemDTOList.forEach(a -> log.info(a));
        //리스폰스디티오만들고 페이지 스타트 엔드 라스트엔드 이전페이지 다음페이지 다 만들자


        ResponsePageDTO<CartItemListDTO> cartItemListDTOResponsePageDTO
                = new ResponsePageDTO<>(cartItemDTOList, requestPageDTO.getPage(),(int) cartItemPage.getTotalElements());




        return cartItemListDTOResponsePageDTO;

    }






}
