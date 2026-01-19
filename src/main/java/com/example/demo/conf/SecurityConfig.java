package com.example.demo.conf;

import com.example.demo.constant.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.annotation.WebListener;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@WebListener
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain  filterChain(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity
		
		//권한 페이지에 대한 권한 접속 확인
			.authorizeHttpRequests(
					
					authorization  -> authorization

					.requestMatchers("/memo/register" , "/reply/register").authenticated()	//로그인한 사람만 접속가능
					
					.requestMatchers("/user/signup").permitAll()	//누구나 접속가능
					.requestMatchers("/user/login").permitAll()
					.requestMatchers("/admin/item/**").hasRole(Role.ADMIN.name())
					.requestMatchers("/cart/**").authenticated()
					.requestMatchers("/orders/**").authenticated()




					.requestMatchers("/board/update", "/board/del", "/board/mylist").authenticated()
					.anyRequest().permitAll()
					
				)
			
			// 위변조 방지 웹에서 form태그 변경시 등의 위변조를 방지
			.csrf(csrf -> csrf.disable())
			
			//로그인
			.formLogin(
					
					a -> a

					.loginPage("/user/login")  //로그인을 하는 페이지가 어디인가 ??
					.defaultSuccessUrl("/memo/list")	//로그인이 성공하면 어디로 이동할것인가?
					.usernameParameter("email")		//로그인하는 id가 무엇인가??

					)
			
			//로그아웃
			.logout(
					
					logout -> logout
					.logoutUrl("/user/logout")  		//로그아웃 url은 어디인가?
					.logoutSuccessUrl("/board/list")  //로그아웃 성공후 어디로 보낼것인가?
					.invalidateHttpSession(true)		//로그인성공후 세션 << 삭제
					
					)
				.exceptionHandling(
				a -> a.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
						.accessDeniedHandler(new CustomAccessDeniedHandler()   )


		)



		;


			
			
			
			
		
		return httpSecurity.build();
		
		
		
		
	}
	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	

}
