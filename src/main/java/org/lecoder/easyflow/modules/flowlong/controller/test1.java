package org.lecoder.easyflow.modules.flowlong.controller;


import cn.hutool.json.JSONObject;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class test1 {
    private static final String APP_ID = "AK20250805KCTMGF";
    private static final String APP_SECRET = "9cc1629f86d0105c3db9c2f1290dc21d";
    private static final String UPLOAD_API = "https://openapi.wps.cn/v1/files/upload";

    private static final String TOKEN_URL = "https://openapi.wps.cn/oauth2/token";
    private static final Logger logger = Logger.getLogger(test1.class.getName());

    // 获取access_token（有效期2小时）
    public static String getAccessToken() throws IOException {
        OkHttpClient client = new OkHttpClient();
        // 构建请求体，按照 application/x-www-form-urlencoded 格式
        RequestBody requestBody = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .add("client_id", APP_ID)
                .add("client_secret", APP_SECRET)
                .build();

        // 构建请求
        Request request = new Request.Builder()
                .url(TOKEN_URL)
                .post(requestBody)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = null;
            if (response.body() != null) {
                responseBody = response.body().string();
            }
            return new JSONObject(responseBody).getStr("access_token");
        }
    }


    public static void main(String[] args) throws IOException {
//        String accessToken = getAccessToken();
//        System.out.println(accessToken);
        uploadFile(null, null);
    }

    // 上传文件并返回fileId
    public static String uploadFile(String localFilePath, String fileName) throws IOException {
        String accessToken = getAccessToken();
        OkHttpClient client = new OkHttpClient();

        // 构建文件上传请求
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "测试变价.doc",
                        RequestBody.create(new File("C:\\Users\\Administrator\\Desktop\\测试变价.doc"),
                                MediaType.parse("application/octet-stream")))
                .build();

        Request request = new Request.Builder()
                .url(UPLOAD_API + "?access_token=" + accessToken)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            return new JSONObject(responseBody).getJSONObject("data").getStr("file_id");
        }
    }

    // 生成编辑凭证
    public String generateEditToken(String fileId, String userId, String userName) throws IOException {
        String accessToken = getAccessToken();
        String editUrl = "https://openapi.wps.cn/v1/files/" + fileId + "/edit";

        // 配置编辑参数（权限、用户信息等）
        JSONObject json = new JSONObject();
        // 系统用户唯一标识
        json.put("user_id", userId);
        // 显示的用户名
        json.put("user_name", userName);
        // 权限：write（可编辑）/read（只读）
        json.put("perm", "write");
        // 开启多人协作
        json.put("collab", true);

        RequestBody requestBody = RequestBody.create(
                json.toString(),
                MediaType.parse("application/json")
        );

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(editUrl + "?access_token=" + accessToken)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            return new JSONObject(responseBody).getJSONObject("data").getStr("edit_token");
        }
    }
}
