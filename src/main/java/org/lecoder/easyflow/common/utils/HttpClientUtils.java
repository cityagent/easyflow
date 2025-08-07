package org.lecoder.easyflow.common.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class HttpClientUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpClientUtils.class);
    private static final RestTemplate restTemplate = new RestTemplate();

    // 公共的 fetchData 方法
    public static <T> Map<String, Object> fetchData(String accessToken, Map<String, Object> params, String url, HttpMethod httpMethod, T body) {
        // 构建请求 URL
        StringBuilder urlBuilder = new StringBuilder(url);
        if (accessToken != null) {
            urlBuilder.append("?access_token=").append(accessToken);
        }

        // 添加其他查询参数
        if (params != null && !params.isEmpty()) {
            params.forEach((key, value) -> {
                if (urlBuilder.indexOf("?") != -1) {
                    urlBuilder.append("&").append(key).append("=").append(value);
                } else {
                    urlBuilder.append("?").append(key).append("=").append(value);
                }
            });
        }

        log.info("Request URL: {}", urlBuilder.toString());

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 构建 HttpEntity
        HttpEntity<T> requestEntity;
        if (httpMethod == HttpMethod.GET) {
            requestEntity = new HttpEntity<>(headers); // GET 请求没有 body
        } else {
            requestEntity = new HttpEntity<>(body, headers); // 其他方法可以传递 body
        }

        // 发起请求
        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    urlBuilder.toString(),
                    httpMethod,
                    requestEntity,
                    Map.class
            );

            // 打印响应日志
            log.info("Response: {}", response.getBody());

            return response.getBody();
        } catch (Exception e) {
            log.error("Error occurred while making request to {}: {}", urlBuilder.toString(), e.getMessage());
            throw new RuntimeException("Failed to fetch data from WeCom API", e);
        }
    }
}

