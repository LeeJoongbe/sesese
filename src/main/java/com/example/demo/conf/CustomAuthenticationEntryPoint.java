package com.example.demo.conf;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        String ajaxHeader = request.getHeader("X-Requested-With");

        System.out.println(ajaxHeader);
        System.out.println(ajaxHeader);
        System.out.println(ajaxHeader);
        System.out.println(ajaxHeader);
        boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);

        if(isAjax) {
            //ajax 요청이면
            System.out.println("ajax 요청 임");
            response.setContentType("application/json:charset=utf-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //401

        }else {
            //ajax요청이 아니고 로그인 안되어 있다면
            System.out.println("ajax 요청이 아님");
            response.sendRedirect("/user/login");
        }



    }
}
