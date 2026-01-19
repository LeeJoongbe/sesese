package com.example.demo.service;


import com.example.demo.constant.Role;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class UserService  implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private ModelMapper modelMapper = new ModelMapper();


    //회원가입 : 이메일을 받아서
    // 이메일로 검색한 회원이 있으면 x
    // 테이블에 insert
    public void signup (UserDTO userDTO){

        log.info("들어온 값 : " +  userDTO);
        UserEntity userEntity =
        userRepository.findByEmail(userDTO.getEmail());

        //회원이 있을때
        if(userEntity != null) {

            throw new IllegalStateException("이미 가입된 회원입니다.");
        }

        //userDTO > entity

        userEntity = modelMapper.map(userDTO, UserEntity.class);
        //setPassword 는 기존 값이 아닌 encoding된거 넣어준다.
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Role role = null;
        if(userDTO.getRole().equals("USER")){
            role = Role.USER;
        }else {
            role = Role.ADMIN;

        }
        //role 권한
        userEntity.setRole(role);

        userRepository.save(userEntity);

    }

    public boolean checkEmail(String  email){

        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity == null) {
            return true;    //회원가입 가능
        }
        return false;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //이메일로 데이터 받아오기
        UserEntity userEntity =
        userRepository.findByEmail(username);

        if(userEntity == null) {
            throw new UsernameNotFoundException("");
        }

        
        return User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .roles(userEntity.getRole().name())
                .build();
    }
}
