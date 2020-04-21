package com.hifo.dataoperation.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * HttpClient工具类
 * 调用远程接口
 *
 * @author whc
 * @date 2019/3/15
 */
public class HttpClientUtil {

    /**
     * 连接主机超时（30s）
     */
    public static final int HTTP_CONNECT_TIMEOUT_30S = 30 * 1000;

    /**
     * 从主机读取数据超时（3min）
     */
    public static final int HTTP_READ_TIMEOUT_3MIN = 180 * 1000;

    /**
     * application/json的post
     */
    public static String httpPost(String url, String jsonParam) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        if (null != jsonParam && !jsonParam.isEmpty()) {
            StringEntity entity = new StringEntity(jsonParam, "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
        }
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(HTTP_READ_TIMEOUT_3MIN)
                .setConnectTimeout(HTTP_CONNECT_TIMEOUT_30S).build();
        httpPost.setConfig(requestConfig);
        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            if (response == null) {
                throw new IOException("无应答");
            }
            HttpEntity entity = response.getEntity();
            String str = EntityUtils.toString(entity, "UTF-8");
            // 此句关闭了流
            EntityUtils.consume(entity);
            return str;
        }
    }

    /**
     * httpGet
     */
    public static String httpGet(String url) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(HTTP_READ_TIMEOUT_3MIN)
                .setConnectTimeout(HTTP_CONNECT_TIMEOUT_30S).build();
        httpGet.setConfig(requestConfig);
        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            if (response == null) {
                throw new IOException("无应答");
            }
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }
    }

}