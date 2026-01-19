package com.example.demo.repository;

import com.example.demo.constant.ItemStatus;
import com.example.demo.entity.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartRepositoryTest {

    @Autowired
    CartRepository cartRepository;      //장바구니 crud가능

    @Autowired
    CartItemRepository cartItemRepository; //장바구니 아이템crud

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void old(){

        //장바구니를 먼저 저장하고 
        // ?번 장바구니가 저장이 되고 
        // ?번 장바구니를 참조하는 아이템 목록들 작성
        //회원참조
        UserEntity userEntity = userRepository.findByEmail("hong@test.com");

//        Cart cart = new Cart();
//        cart.setUserEntity(userEntity);       //회원을 참조하는 장바구니
//
//        cart = cartRepository.save(cart);          //저장
        Cart cart = cartRepository.findById(4L).get();          //저장

        //카트에 저장할 카트아이템이 참조하는 아이템을 찾아오기

        Item item = itemRepository.findById(1L).get();


        CartItem cartItem = new CartItem();
        cartItem.setItem(item);
        cartItem.setCart(cart);

        cartItemRepository.save(cartItem);


//        List<CartItem> cartList = new ArrayList<>();
//        cartList.add(cartItem);

//        cart.setCartItemList();

        
    }

    @Test
    public void oneToMany(){
        //회원
        UserEntity userEntity = userRepository.findByEmail("1");


        //카트만들기
        Cart cart = new Cart();
        cart.setUserEntity(userEntity);     //회원참조

        //장바구니 아이템들
        List<CartItem> cartItemList = new ArrayList<>();

        //장바구니에 넣을 아이템들 찾아오기
        Item item = itemRepository.findById(1L).get();

        //장바구니 아이템 객체 만들기
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);     //참조할 장바구니
        cartItem.setCount(7);       //장바구니에 담을 아이템수량
        cartItem.setItem( item);    //장바구니아이템이 참조하는 아이템

        CartItem cartItem1 = new CartItem();
        cartItem1.setCart(cart);     //참조할 장바구니
        cartItem1.setCount(7);       //장바구니에 담을 아이템수량
        cartItem1.setItem( item);    //장바구니아이템이 참조하는 아이템

        CartItem cartItem2 = new CartItem();
        cartItem1.setCart(cart);     //참조할 장바구니
        cartItem1.setCount(7);       //장바구니에 담을 아이템수량
        cartItem1.setItem( item);    //장바구니아이템이 참조하는 아이템
        
        //장바구니 리스트에 넣기
        cartItemList.add(cartItem);     //1번아이템을 여러개 담기
        cartItemList.add(cartItem1);     //1번아이템을 여러개 담기
        cartItemList.add(cartItem2);     //1번아이템을 여러개 담기


        // 예) 생수 7개
        //     생수 7개
        //     생수 7개

        cart.setCartItemList(cartItemList); //장바구니에  살 장바구니목록 담기


        cartRepository.save(cart);      //카트만 저장한다.  왜 ? 카트에 set으로 장바구니아이템목록을 담아놔서
                                    //같이 저장된다.

    }



    @Test
    public void insertItemTest(){


        for(int i = 0; i < 100; i++) {
            UserEntity userEntity  = userRepository.findByEmail("hong@test.com");

            Item item = new Item();
            item.setIName("상품명" + i);
            item.setIDetail("상세설명" + i);
            item.setIPrice(100 + i);
            item.setICount( 1 + i);
            if( i % 2 == 0) {
                item.setItemStatus(ItemStatus.SOLDOUT);
            }else {
                item.setItemStatus(ItemStatus.SELL);
            }
            item.setUserEntity(userEntity);
            itemRepository.save(item);

        }



    }

    @Test
    @Transactional
    @Rollback(false)
    public void cartInsertTest(){

        //누가 만든 장바구니 인가
        String email  = "hong@test.com";    //로그인한 대상

        //로그인한 대상의 엔티티 찾아오기
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null) {
            System.out.println("로그인 안함");
        }

        //장바구니를 만든다.  : 장바구니는 유니크로 되어있다. 1:1 관계 그래서
        // 장바구니의 만든이를 통해서 이미 있는지 조인해서 찾아봐야한다.

        Cart cart = cartRepository.findByUserEntityEmail(email);
        // 장바구니가 이미 있다면  만들필요 없이 위에꺼 없다면 만들자
        if(cart == null) {
            cart = new Cart();
            cart.setUserEntity(userEntity);
        }

        //장바구니에 아이템들을 한번에 넣었다면 11, 12, 13 ,14 번을 받아온 상황
        List<CartItem> cartItemList = cartRepository.findByUserEntityEmail(email).getCartItemList();

        for(int i = 11 ; i < 16; i++){

            Item item = itemRepository.findById( Long.valueOf( i )) .get()  ;

            CartItem cartItem = new CartItem();
            cartItem.setItem(item);     //사는 아이템
            cartItem.setCount( 8  + i); //사는 수량
            cartItem.setCart(cart);     //새로 만들어질 카트나 (pk값이 있는 cart객체는 저장된 객체
                                        // 없는 객체는 새로 만들어질 객체
            cartItemList.add(cartItem);

            cartItemRepository.save(cartItem);


        }

        cart.setCartItemList(  cartItemList );
        cartRepository.save(cart);



    }



    @Test
    @Transactional
    @Rollback(false)
    public void readCartItemList(){


        String email = "hong@test.com";

        Cart cart = cartRepository.selectUserEmail(email);

        System.out.println(cart);

//        for( CartItem  cartItem :  cart.getCartItemList() ) {
//            System.out.println(cartItem);
//        }
//
//        //잘 가져온  cart.getCartItemList()
//        // 삭제를 하고 싶다 변경을 하고 싶다 : 고아객체
//        CartItem cartItem =
//                cart.getCartItemList().get(0);
//
//        cart.getCartItemList().remove(cartItem  );
//
//
//
//        cartItem.setCart(null);




    }

    @Test
    public void  aa(){

        Board board = new Board();
        board.setTitle("ddd");
        board.setContent("asdfsadf");

        Board board1 = Board.builder()
                .content("내용")
                .title("제목")
                .build();

        Aaaa a  = new Aaaa();
        a.setContent("안녕").setContent("안녕 나는 내용이야").setTitle("나는 제목이고")
                .setWriter("신짱구");

    }

    @Test
    public  void register100(){


        Cart cart = cartRepository.findByUserEntityEmail("sin@test.com");

        for(int i = 0; i< 300; i++){
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setCount(100);
            cartItem.setItem(itemRepository.findById(11L).get());

            cartItemRepository.save(cartItem);

        }


    }







}