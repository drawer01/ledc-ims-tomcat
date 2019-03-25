package com.ledc.ims.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.ledc.ims.Entity.JwtResponseBody;
import com.ledc.ims.Utils.JwtTokenUtils;
import com.ledc.ims.Utils.JwtUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException{
        JwtResponseBody responseBody = new JwtResponseBody();
        responseBody.setStatus("200");
        responseBody.setMsg("Login Success!");

        JwtUser userDetails = (JwtUser) authentication.getPrincipal();

        String jwtToken = JwtTokenUtils.createToken(userDetails.getUsername(), 300);
        responseBody.setJwtToken(jwtToken);

        httpServletResponse.getWriter().write(JSON.toJSONString(responseBody));
    }

}
