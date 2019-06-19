package com.study.radon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.radon.community.dto.AccessTokenDTO;
import com.study.radon.community.dto.GithubUserDTO;
import com.study.radon.community.provider.GithubProvider;

/**
 * github oAuth login
 * @author Radon
 * @since 2019/6/19
 */
@Controller
public class AuthorizeController {
    
    @Autowired
    GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("ac89688cc08c49e3f4d4");
        accessTokenDTO.setClient_secret("5c5049f718d92b7d3d17b5d707c2755f81ecaca3");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setState(state);
        // 获取access_token
        String access_token = githubProvider.getAccessToken(accessTokenDTO);
        // 根据access_token获取user信息
        GithubUserDTO userInfo = githubProvider.getUser(access_token);
        String bio = userInfo.getBio();
        return "index";
    }
    
}
