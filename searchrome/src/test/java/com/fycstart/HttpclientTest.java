package com.fycstart;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author fyc
 * @description: TODO
 * @date 2019/5/8下午 8:25
 */
public class HttpclientTest {


    private static RequestConfig requestConfig = null;

    static {
        // 设置请求和传输超时时间
        requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
    }

    /**
     * post请求传输json参数
     *
     * @param url url地址
     * @param 参数
     * @return
     */
    public static JSONObject httpPost(String url, JSONArray jsonParam) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        // 设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("token", "eyJhbGciOiJIUzUxMiJ9.eyJDSEFOTkVMLUtFWSI6IkdPT0dMRVBMQVkiLCJVU0VSLUlEIjoxMDAwMjA5MSwiZXhwIjoxNTU4NTI4ODY5LCJQSE9ORSI6IjgzODcxNjQ5Nzk3In0.7KUY0YdUgoGY9sYUbbreye3B3vVP1CDVUrSPBBIqcYfzSqG_aorG-MUPygs1eTnVk_y9ErilXS_YxymWWLS9Vw");
        try {
            if (null != jsonParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = "";
                try {
                    // 读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity(), "utf-8");
                    // 把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(str);
                } catch (Exception e) {
                    System.out.println("post请求提交失败:" + url + "  --->" + e);

                }
            }
        } catch (IOException e) {
            System.out.println("post请求提交失败:" + url + "---->" + e);
        } finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }


    public static void main(String[] args) {
        String url = "http://ksp-app-test.cf57eaa4fd3624fc39ab5ed0955c6048a.cn-beijing.alicontainer.com/appserver/v1/deviceinfo/upload-user-app-data";
        String param = "[{\n" +
                "\t\"appName\":\"快手\",\n" +
                "\t\"packageName\":\"com.kuaishou\"\n" +
                "},\n" +
                "{\n" +
                "\t\"appName\":\"微信\",\n" +
                "\t\"packageName\":\"com.weixin\"\n" +
                "},\n" +
                "{\n" +
                "\t\"appName\":\"支付宝\",\n" +
                "\t\"packageName\":\"com.zhifubao\"\n" +
                "}\n" +
                "]";
        JSONArray objects = JSON.parseArray(param);

        JSONObject reObject = httpPost(url, objects);
        System.out.println(reObject);
    }

}
