package com.study.radon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private GithubProvider githubProvider;
    
    @Value("${github.client.id}")
    private String clientId;
    
    @Value("${github.client.secret}")
    private String clientSecret;
    
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        // 获取access_token
        String access_token = githubProvider.getAccessToken(accessTokenDTO);
        // 根据access_token获取user信息
        GithubUserDTO userInfo = githubProvider.getUser(access_token);
        System.out.println(userInfo.getName());
        return "index";
    }
    
}
