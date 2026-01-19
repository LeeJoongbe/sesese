package com.example.demo.repository;

import com.example.demo.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {


    public Cart findByUserEntityEmail (String email);

    @Query("select c from Cart c where c.userEntity.email =:email")
    public Cart selectUserEmail (String email);

    @Query(value = "select * from cart c join  user_entity u on c.uno = u.uno where u.email = :email" , nativeQuery = true)
    public Cart selectUserEmailNative (String email);

}
