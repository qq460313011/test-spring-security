package com.test.springsecuritytest.config;

import com.test.springsecuritytest.services.SpringDataUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    private SpringDataUserDetailsService springDataUserDetailsService;
//
//    public SecurityConfig(SpringDataUserDetailsService springDataUserDetailsService) {
//        this.springDataUserDetailsService = springDataUserDetailsService;
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(springDataUserDetailsService);
//    }
//
//    @Bean
//    @Override
//    protected AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        //1.直接明文比对
        //return NoOpPasswordEncoder.getInstance();
        //2.BCryptPasswordEncoder加密比对
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()  //  允许所有用户访问"/"和"/index"
                .antMatchers("/page1").hasAnyAuthority("p1") //访问page/page1需要p1权限
                .antMatchers("/page2").hasAnyAuthority("p2") //同上
                .anyRequest().authenticated()    // 其他地址的访问均需验证权限
                .and()
                .formLogin()//开启表单登录
                .loginPage("/login")   //  登录页
                .successForwardUrl("/index")  //登录成功后的页面地址;
                .and()
                .logout()  //添加默认退出登录支持，访问URL/logout,会清理Session,清除SecurityContextHolder,重定向到/login?success
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");

        http.csrf().disable();  //想要使用get方式logout,必须关闭csrf认证

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED); //管理Session创建的方式
    }


}
