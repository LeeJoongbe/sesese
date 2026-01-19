package com.example.demo.service;


import com.example.demo.dto.CartItemDTO;
import com.example.demo.entity.CartItem;
import com.example.demo.repository.CartItemRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    //장바구니 수량을 변경하는 서비스
    public void updateCount(CartItemDTO cartItemDTO){

        log.info("서비스로 들어온 값 : " + cartItemDTO);

        //카트아이템을 찾아와서 데이터를 수정한다.
        //@Transactional 가 달려있기 때문에 엔티티를 가져와서
        // 수정하면 save를 따로 하지 않아도 엔티티매니져가 변경점을 찾아서
        // update문을 실행한다. 트랜잭션이 끝날때
        CartItem cartItem =
                cartItemRepository.findById(cartItemDTO.getItemid())
                        .orElseThrow(EntityNotFoundException::new);

        cartItem.setCount(cartItemDTO.getCount());  //수량변경

    }


    public  void del(Long[] id , String email) throws Exception {

        if(id != null && id.length > 0) {
            for (Long num  : id  ) {
                CartItem cartItem =
                cartItemRepository.findById(num).orElseThrow(EntityNotFoundException::new);
                if( ! cartItem.getCart().getUserEntity().getEmail().equals(email)){
                    throw new Exception("너님꺼 아님");
                }

                cartItemRepository.delete(cartItem);

            }
        }

    }


}
