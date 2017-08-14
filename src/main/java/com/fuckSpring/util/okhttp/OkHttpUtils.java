package com.fuckSpring.util.okhttp;

import okhttp3.*;
import okhttp3.internal.Util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 进度:
 * 1. 完成同步Get请求
 * 2. 完成异步Get请求
 * 3. 完成同步Post表单请求
 * 4. 完成异步Post表单请求
 * 5. 完成同步PostJSON请求
 * 6. 完成异步PostJSON请求
 *
 * @Author: norman
 */
public final class OkHttpUtils {

    // 使用默认配置
    // 注意：dispatcher中维护了一个线程池，限制了异步请求数：同一主机一次最多5个请求；若不同主机，并发限制在64个
    // 可进行修改，类似于client.dispatcher().setMaxRequestsPerHost()或client.dispatcher().setMaxRequests();
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS).build();

    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    private OkHttpUtils() {
    }

    private static class Method {
        public static final String GET = "get";
        public static final String POST = "post";
    }

    /**
     * 组装请求头
     *
     * @param headersMap
     * @return
     */
    private static void makeHeaders(Request.Builder builder, Map<String, String> headersMap) {
        if (headersMap == null || headersMap.size() < 1) {
            return;
        }
        for (Map.Entry<String, String> entry : headersMap.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 组装GET 请求的URL
     *
     * @param paramsMap
     * @return
     */
    private static String makeGetParams(Map<String, String> paramsMap) {
        if (null == paramsMap || paramsMap.size() < 1) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("?");
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            sb.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * 发起同步GET请求
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static Response get(String url) throws IOException {
        return get(url, null, null);
    }

    /**
     * 发起同步GET请求
     *
     * @param url
     * @param paramsMap
     * @return
     * @throws IOException
     */
    public static Response get(String url, Map<String, String> paramsMap) throws IOException {
        return get(url, paramsMap, null);
    }

    /**
     * 发起同步GET请求
     *
     * @param url        请求地址
     * @param paramsMap  请求参数
     * @param headersMap 请求头参数
     * @return 返回Response对象
     * @throws IOException 请求被取消，未获取到返回体
     */
    public static Response get(String url, Map<String, String> paramsMap, Map<String, String> headersMap) throws IOException {

        if (url == null) {
            return null;
        }
        // 0. 创建新请求
        Request.Builder reqBuilder = new Request.Builder();

        // 1. 确定请求方式GET
        reqBuilder.get();

        // 2. 组装URL
        String urlParams = makeGetParams(paramsMap);
        reqBuilder.url(urlParams == null ? url : url + urlParams);

        // 3. 组装Header
        makeHeaders(reqBuilder, headersMap);


        // 4. 组装完毕开始请求
        return client.newCall(reqBuilder.build()).execute();
    }

    /**
     * 发起异步GET请求
     *
     * @param url      请求地址
     * @param callback
     */
    public static void getAsyc(String url, Callback callback) {
        getAsyc(url, null, null, callback);
    }

    /**
     * 发起异步GET请求
     *
     * @param url       请求地址
     * @param paramsMap 请求参数
     * @param callback
     */
    public static void getAsyc(String url, Map<String, String> paramsMap, Callback callback) {
        getAsyc(url, paramsMap, null, callback);
    }

    /**
     * 发起异步GET请求
     *
     * @param url        请求地址
     * @param paramsMap  请求参数
     * @param headersMap 请求头参数
     * @param callback
     */
    public static void getAsyc(String url, Map<String, String> paramsMap, Map<String, String> headersMap, Callback callback) {

        // 0. 创建新请求
        Request.Builder reqBuilder = new Request.Builder();

        // 1. 确定请求方式GET
        reqBuilder.get();

        // 2. 组装URL
        String urlParams = makeGetParams(paramsMap);
        reqBuilder.url(urlParams == null ? url : url + urlParams);

        // 3. 组装Header
        makeHeaders(reqBuilder, headersMap);

        client.newCall(reqBuilder.build()).enqueue(callback);
    }

    public static Response post(String url) throws IOException {
        return post(url, null, null);
    }

    public static Response post(String url, Map<String, String> paramsMap) throws IOException {
        return post(url, paramsMap, null);
    }

    /**
     * 同步表单Post请求
     *
     * @param url        请求地址
     * @param paramsMap  请求参数
     * @param headersMap 请求头参数
     * @return Response
     * @throws IOException 取消请求造成的异常
     */
    public static Response post(String url, Map<String, String> paramsMap, Map<String, String> headersMap) throws IOException {

        // 0. 创建新请求
        Request.Builder reqBuilder = new Request.Builder();

        // 1. 组装请求体
        reqBuilder.post(makePostPramasByForm(paramsMap));

        // 2. 组装URL
        reqBuilder.url(url);

        // 3. 组装Header
        makeHeaders(reqBuilder, headersMap);
        // reqBuilder.addHeader("ddg", "ggh");

        //4. 发送请求
        return client.newCall(reqBuilder.build()).execute();
    }

    public static void postAsyc(String url, Callback callback) {
        postAsyc(url, null, null, callback);
    }

    public static void postAsyc(String url, Map<String, String> paramsMap, Callback callback) {
        postAsyc(url, paramsMap, null, callback);
    }

    public static void postAsyc(String url, Map<String, String> paramsMap, Map<String, String> headersMap, Callback callback) {
        // 0. 创建新请求
        Request.Builder reqBuilder = new Request.Builder();

        // 1. 组装请求体
        reqBuilder.post(makePostPramasByForm(paramsMap));

        // 2. 组装URL
        reqBuilder.url(url);

        // 3. 组装Header
        makeHeaders(reqBuilder, headersMap);

        //4. 发送请求
        client.newCall(reqBuilder.build()).enqueue(callback);
    }

    public static Response postJson(String url, String jsonStr) throws IOException {
        return postJson(url, jsonStr, null);
    }

    public static Response postJson(String url, String jsonStr, Map<String, String> headersMap) throws IOException {

        // 0. 创建新请求
        Request.Builder reqBuilder = new Request.Builder();

        // 1. 组装请求体
        reqBuilder.post(RequestBody.create(JSON, jsonStr));

        // 2. 组装URL
        reqBuilder.url(url);

        // 3. 组装Header
        makeHeaders(reqBuilder, headersMap);

        //4. 发送请求
        return client.newCall(reqBuilder.build()).execute();
    }

    public static void postJsonAsyc(String url, String jsonStr, Callback callback) {
        postJsonAsyc(url, jsonStr, null, callback);
    }

    public static void postJsonAsyc(String url, String jsonStr, Map<String, String> headersMap, Callback callback) {

        // 0. 创建新请求
        Request.Builder reqBuilder = new Request.Builder();

        // 1. 组装请求体
        reqBuilder.post(RequestBody.create(JSON, jsonStr));

        // 2. 组装URL
        reqBuilder.url(url);

        // 3. 组装Header
        makeHeaders(reqBuilder, headersMap);

        //4. 发送请求
        client.newCall(reqBuilder.build()).enqueue(callback);
    }

    /**
     * 根据表单请求参数生成RequestBody
     *
     * @param paramsMap 请求参数
     * @return RequestBody
     */
    private static RequestBody makePostPramasByForm(Map<String, String> paramsMap) {
        if (paramsMap == null || paramsMap.size() < 1) {
            return Util.EMPTY_REQUEST;
        }
        FormBody.Builder formBody = new FormBody.Builder();
        for (String key : paramsMap.keySet()) {
            formBody.add(key, paramsMap.get(key));
        }
        return formBody.build();
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        Map<String, String> p = new HashMap<>();
        p.put("uuu", "da");
        p.put("ooo", "da1");
        Map<String, String> h = new HashMap<>();
        h.put("dhd", "dagh");

        String js = "{\n" +
                "  \"uuu\":\"345\",\n" +
                "  \"ooo\":\"ttj\"\n" +
                "}";
        System.out.println(OkHttpUtils.postJson("http://localhost:8999/oktest", js, h).body().string());
    }
}
