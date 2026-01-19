package com.example.demo.service;


import com.example.demo.constant.ItemStatus;
import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
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

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final OrdersItemRepository ordersItemRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;


    //상품 주문
    public String register(OrderItemDTO orderItemDTO , String email){

        log.info("데이터 :  " + orderItemDTO);

        //email를 이용해서 주문을 만들어서 주문안에 주문아이템을 넣는다.
        UserEntity userEntity = userRepository.findByEmail(email);

        Orders orders = Orders.builder()
                .userEntity(userEntity)
                .build();


        //사는 아이템
        Item item = itemRepository.findById(orderItemDTO.getOino())
                .orElseThrow(EntityNotFoundException::new);

        //주문 아이템
        OrderItem orderItem = OrderItem.builder()
                .item(item)     //사는 아이템
//                .orders(orders)       // 사는 주문
                .oiCount(orderItemDTO.getOiCount())      // 수량
                .build();

        //저장전 수량확인 : 주문수량보다 재고수량이 작다면 재고수량이 0이라면 품절이라면

        if(item.getICount() == 0 || orderItem.getOiCount()   > item.getICount()
        || item.getItemStatus().name().equals(ItemStatus.SOLDOUT.name())) {
            //미판매 예외처리
            return null;
        }





        //저장하고 엔티티를 반환한다.
        orders = ordersRepository.save(orders);
        orderItem.setOrders(orders);
        orderItem = ordersItemRepository.save(orderItem);

        return orderItem.getItem().getIName();//주문한 상품명
    }


    //주문 리스트
    public ResponsePageDTO list(RequestPageDTO requestPageDTO , String  email) {
        Pageable pageable = PageRequest.of(requestPageDTO.getPage() -1,10, Sort.by("ono"));
        Page<Orders> ordersPage =
        ordersRepository.selectByEmail(email ,pageable);
        List<Orders>  itemList = ordersPage.getContent();
        List<OrdersDTO> c = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (Orders orders : itemList) {
            OrdersDTO ordersDTO = modelMapper.map(orders, OrdersDTO.class);
            List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
            //변환 주문에 있는 주문아이템들
            for(OrderItem orderItem    :orders.getOrderItemsList()) {
                OrderItemDTO orderItemDTO = modelMapper.map(orderItem, OrderItemDTO.class);
                orderItemDTO.setItemDTO(  modelMapper.map(orderItem.getItem() , ItemDTO.class) );
                orderItemDTOList.add(orderItemDTO);
            }
            ordersDTO.setOrderItemDTOList(orderItemDTOList);
            c.add(ordersDTO);
        }
        ResponsePageDTO<OrdersDTO> a
                = new ResponsePageDTO<>(c , requestPageDTO.getPage() , (int) ordersPage.getTotalElements());
        return a;
    }



    public void orders(Long[] nums , String email) throws Exception {

        // 사용자 정보 찾기
        Cart cart = cartRepository.findByUserEntityEmail(email);

        UserEntity userEntity = cart.getUserEntity();

        //주문
        Orders orders = new Orders();
        orders.setUserEntity(userEntity);   //주문자 설정
        //장바구니에서 체크된 주문하는 아이템들
        List<OrderItem> orderItemList = new ArrayList<>();

        for( int i = 0; i < nums.length; i++){
            CartItem cartItem   = cartItemRepository.findById(nums[i]).orElseThrow(EntityNotFoundException::new);

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(cartItem.getItem());  //사는 아이템
            orderItem.setOiCount(cartItem.getCount());  //사는 수량

            //아이템의 수량을 변경  // 아이템의 수량이 0 이하거나 사는 수량보다 적다면 미판매 // 수량0이면
            // 품절

            Item item = cartItem.getItem();
            if(item.getICount() == 0  ||
                    item.getICount() < cartItem.getCount() ||
                    item.getItemStatus().name().equals(ItemStatus.SOLDOUT.name())){
                //미판매 예외처리
                log.info( " 사려는 아이템 : " + item);
                
                throw new Exception("상품미판매");
            }
            //아이템의 수량에서 - 장바구니에서 선택한 아이템수량
            item.setICount( item.getICount()  - cartItem.getCount()  );
            if(item.getICount() == 0){
                item.setItemStatus(ItemStatus.SOLDOUT); //품절처리
            }

            item.setSalesQuantity(  item.getSalesQuantity() +  Long.valueOf(cartItem.getCount()));


            orderItem.setOrders(orders);

            orderItemList.add(orderItem);

            cartItemRepository.delete(cartItem);

        }

        orders.setOrderItemsList(orderItemList); //주문하는 아이템들을 넣어주기 위해서

        ordersRepository.save(orders);
        
        
    }





}
