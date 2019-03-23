package com.ledc.ims.config;

import com.ledc.ims.Filter.JwtAuthenticationFilter;
import com.ledc.ims.Filter.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true) //开启security注解
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //private final Logger logger = (Logger) LoggerFactory.getLogger(getClass());

    private UserDetailsService userDetailsService;
    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private void setUserDetailsService(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }
    // 加密密码
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    // roles admin allow to access /admin/**
    // roles user allow to access /user/**
    // custom 403 access denied handler
    //重写了其中的configure（）方法设置了不同url的不同访问权限
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/tasks/**").authenticated()
                    .anyRequest().permitAll()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                //.and()
                //.formLogin()
                    //.loginPage("/login.html")
                    //.loginProcessingUrl("/login")
                    //.passwordParameter("password")
                    //.usernameParameter("username")
                    //.successForwardUrl("/success.html")
                    //.failureForwardUrl("/error.html")
                    //.defaultSuccessUrl()//如果用户没有访问受保护的页面，默认跳转到页面
                    //.failureUrl()
                    //.failureHandler(AuthenticationFailureHandler)
                    //.successHandler(AuthenticationSuccessHandler)
                    //.failureUrl("/login?error")
                    //.permitAll()
                //.and()
                //.logout()
                    //.permitAll()
                //.and()
                //.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

}
