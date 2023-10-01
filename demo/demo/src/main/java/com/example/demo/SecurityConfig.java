package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
  @Bean
    AuthenticationProvider authenticationProvider(){
      DaoAuthenticationProvider provider
              = new DaoAuthenticationProvider();
      provider.setUserDetailsService(userDetailsService);
      provider.setPasswordEncoder(passwordEncoder());
      return provider;
  }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/home").hasAuthority("user")
                .antMatchers("/admin").hasAuthority("admin")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login") // Trang đăng nhập tùy chỉnh
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .logoutSuccessUrl("/") // Đường dẫn sau khi đăng xuất thành công
                .permitAll()
                .and()
                .sessionManagement()
                .invalidSessionUrl("/") // Đường dẫn khi phiên đăng nhập hết hạn
                .maximumSessions(1) // Giới hạn 1 phiên đăng nhập đồng thời cho mỗi tài khoản
                .maxSessionsPreventsLogin(true); // Ngăn chặn đăng nhập nếu đã có phiên đăng nhập khác
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
