package com.ledc.ims.config;

import com.ledc.ims.Filter.JwtAuthenticationTokenFilter;
import com.ledc.ims.ServiceImpl.JwtAccessDeniedHandle;
import com.ledc.ims.ServiceImpl.JwtAuthenticationFailureHandler;
import com.ledc.ims.ServiceImpl.JwtAuthenticationSuccessHandler;
import com.ledc.ims.ServiceImpl.JwtLogoutSuccessHandler;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true) //开启security注解
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //private final Logger logger = (Logger) LoggerFactory.getLogger(getClass());

    private UserDetailsService userDetailsService;
    private JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    private JwtAccessDeniedHandle jwtAccessDeniedHandler;
    private JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private void setUserDetailsService(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }
    @Autowired
    private void setJwtAuthenticationFailureHandler(JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler){
        this.jwtAuthenticationFailureHandler = jwtAuthenticationFailureHandler;
    }
    @Autowired
    private void setJwtAuthenticationSuccessHandler(JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler){
        this.jwtAuthenticationSuccessHandler = jwtAuthenticationSuccessHandler;
    }
    @Autowired
    private void setJwtAuthenticationTokenFilter(JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter){
        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
    }
    @Autowired
    private void setJwtAccessDeniedHandler(JwtAccessDeniedHandle jwtAccessDeniedHandler){
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }
    @Autowired
    private void setJwtLogoutSuccessHandler(JwtLogoutSuccessHandler jwtLogoutSuccessHandler){
        this.jwtLogoutSuccessHandler = jwtLogoutSuccessHandler;
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
                    .antMatchers("/v2/api-ims/tasks/**").authenticated()
                    .anyRequest().permitAll()
                //.and()
                //.rememberMe()
                .and()
               // .addFilter(new JwtAuthenticationFilter(authenticationManager()))
               // .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                    .successHandler(jwtAuthenticationSuccessHandler)
                    .failureHandler(jwtAuthenticationFailureHandler)
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
                    .permitAll()
                .and()
                .logout()
                    .logoutSuccessHandler(jwtLogoutSuccessHandler)
                    .permitAll();
                //.and()
                //.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        // 记住我
        http.rememberMe().rememberMeParameter("remember-me")
                .userDetailsService(userDetailsService).tokenValiditySeconds(300);
        http.exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler); // 无权访问 JSON 格式的数据
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class); // JWT Filter

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

}
