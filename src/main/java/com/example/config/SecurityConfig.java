package com.example.config;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		//認可の設定
		http.authorizeRequests()
		    .antMatchers("/loginForm").permitAll()
	        .anyRequest().authenticated();
		
		//ログイン処理
		http.formLogin()
		    .loginProcessingUrl("/login")
		    .loginPage("/loginForm")
		    .usernameParameter("email")
		    .passwordParameter("password")
		    .defaultSuccessUrl("/home", true)
		    .failureUrl("/loginForm?error");
		
		//ログアウト処理
		http.logout()
		    .logoutUrl("/logout") //ログアウト処理のパス
		    .logoutSuccessUrl("/loginForm"); //ログアウト成功後のパス
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/css/**", "/js/**", "/images/**");
	}
}
