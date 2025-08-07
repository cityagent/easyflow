package org.lecoder.easyflow.modules.flowlong.service.impl;


import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import org.lecoder.easyflow.common.config.WeComConfig;
import org.lecoder.easyflow.common.entity.WeComTokenRequest;
import org.lecoder.easyflow.common.utils.HttpUtils;
import org.lecoder.easyflow.modules.flowlong.service.IWeComTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.Optional;

@Service
public class WeComTokenServiceImpl implements IWeComTokenService {

    private static final Logger log = LoggerFactory.getLogger(WeComTokenServiceImpl.class);

    @Resource
    private WeComConfig config;
    @Override
    public String getTokenByRequest(WeComTokenRequest tokenRequest) {
        String url = config.getBaseUrl() + config.getEndpoints().get("get-token");
        if (tokenRequest == null) {
            throw new IllegalArgumentException("未输入参数");
        }
        // 构造查询参数
        String param = String.format("corpid=%s&corpsecret=%s",
                URLEncoder.encode(Optional.ofNullable(tokenRequest.getCorpid()).orElseThrow(()-> new IllegalArgumentException("未输入id"))),
                URLEncoder.encode(Optional.ofNullable(tokenRequest.getCorpsecret()).orElseThrow(()-> new IllegalArgumentException("未输入密钥")))
        );

        log.info("Requesting WeCom token from URL: {}", url);

        // 发送 GET 请求
        String responseStr = HttpUtils.sendGet(url, param);

        log.info("Token response: {}", responseStr);

        // 解析 JSON 响应
        try {
            JSONObject responseJson = new JSONObject(responseStr);
            if (responseJson.get("access_token")!= null) {
                return responseJson.getStr("access_token");
            } else {
                log.error("Failed to get access_token, response: {}", responseStr);
                throw new RuntimeException("WeCom API error: " + responseStr);
            }
        } catch (JSONException e) {
            log.error("Failed to parse WeCom token response: {}", responseStr, e);
            throw new RuntimeException("Invalid JSON response from WeCom API", e);
        }
    }

    @Override
    public String getToken(String secret) {
        String corpId= config.getComCredentials().get("corpid");
        if (StringUtils.isEmpty(secret)){
            secret = config.getComCredentials().get("corpsecret");
        }
        WeComTokenRequest tokenRequest=new WeComTokenRequest();
        tokenRequest.setCorpid(corpId);
        tokenRequest.setCorpsecret(secret);
        return getTokenByRequest(tokenRequest);
    }
}
