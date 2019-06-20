package com.study.radon.community.provider;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.study.radon.community.dto.AccessTokenDTO;
import com.study.radon.community.dto.GithubUserDTO;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 使用OkHttp发起POST请求
 * 
 * @author Radon
 * @since 2019/6/17
 */
@Component
public class GithubProvider {

    public static final MediaType TYPE = MediaType.get("application/json; charset=utf-8");

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(TYPE, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder().url("https://github.com/login/oauth/access_token").post(body).build();
        try (Response response = client.newCall(request).execute()) {
            String responseStr = response.body().string();
            /* eg:access_token=ebd2c9611d003662382e0cda7c1ced97ab022528&scope=user&token_type=bearer
             * 将返回值进行拆分得到access_token
             */
            String access_token = responseStr.substring(responseStr.indexOf("=") + 1, responseStr.indexOf("&"));
            return access_token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUserDTO getUser(String access_token) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url("https://api.github.com/user?access_token=" + access_token).build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            // 使用fastjson将响应值转换成GithubUserDTO
            GithubUserDTO githubUserDTO = JSON.parseObject(string, GithubUserDTO.class);
            return githubUserDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
