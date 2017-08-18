package com.bao.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(name->{
//            List<GrantedAuthority> list = new ArrayList<>();
//            list.add(new SimpleGrantedAuthority("USER"));
//            return  new MyUser("ss","22",list);
//        });
            auth.inMemoryAuthentication().withUser("marissa").password("koala").roles("USER").authorities("admin").and().withUser("paul")
                .password("emu").roles("USER");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**", "/images/**", "/oauth/uncache_approvals", "/oauth/cache_approvals","/oauth/check_token");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .formLogin()
//                .loginPage("/login")
                .permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                .permitAll();
//        http
//                .authorizeRequests()
//                .antMatchers("/login.jsp").permitAll()
//                .antMatchers("/oauth/authorize").permitAll()
//                .anyRequest().hasRole("USER")
//                .and()
//                .exceptionHandling()
//                .accessDeniedPage("/login.jsp?authorization_error=true")
//                .and()
//                // TODO: put CSRF protection back into this endpoint
//                .csrf()
//                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
//                .disable()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login.jsp")
//                .and()
//                .formLogin()
//                .loginProcessingUrl("/login")
//                .failureUrl("/login.jsp?authentication_error=true")
//                .loginPage("/login.jsp");
        // @formatter:on


//        http.antMatcher("/**")                                       (1)
//                .authorizeRequests()
//                .antMatchers("/", "/login**", "/webjars/**").permitAll() (2)
//                .anyRequest().authenticated()                            (3)
//                .and().exceptionHandling()
//                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/")) (4)
//        1、All requests are protected by default
//        2、The home page and login endpoints are explicitly excluded
//        3、All other endpoints require an authenticated user
//        4、Unauthenticated users are re-directed to the home page
    }
}
