package com.fuckSpring.util.okhttp;

import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 进度:
 * 1. 完成同步Get请求
 * 2. 完成异步Get请求
 * 3. (未)完成同步Post表单请求
 * 4. (未)完成异步Post表单请求
 * 5. (未)完成同步PostJSON请求
 * 6. (未)完成异步PostJSON请求
 *
 * @Author: norman
 */
public final class OkHttpUtils {

    // 使用默认配置
    // 注意：dispatcher中维护了一个线程池，限制了异步请求数：同一主机一次最多5个请求；若不同主机，并发限制在64个
    // 可进行修改，类似于client.dispatcher().setMaxRequestsPerHost()或client.dispatcher().setMaxRequests();
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS).build();

    private OkHttpUtils() {
    }

    public static void post(String url, Callback callback) {
        post(url, null, null, callback);
    }

    public static void post(String url, Map<String, String> paramsMap, Callback callback) {
        post(url, paramsMap, null, callback);
    }

    public static void post(String url, Map<String, String> paramsMap, Map<String, String> headersMap, Callback callback) {
        //TODO
    }

    public static void postJson(String url, String jsonStr, Callback callback) {
        postJson(url, jsonStr, null, callback);
    }

    public static void postJson(String url, String jsonStr, Map<String, String> headersMap, Callback callback) {
        //TODO
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

    public static void main(String[] args) throws IOException, InterruptedException {
        // HttpsUtils.SSLParams sslSocketFactory = HttpsUtils.getSslSocketFactory(null, null, null);
        // OkHttpClient ok = new OkHttpClient.Builder()
        // .sslSocketFactory(sslSocketFactory.sSLSocketFactory, sslSocketFactory.trustManager)
        // .followRedirects(true)
        // .build();

        // Request request = new Request.Builder()
        //         .get()
        //         .url("https://www.baidu.com/")
        //         .build();
        // System.out.println(Thread.currentThread().getId() + "\tjjjj");
        // ok.newCall(request)
        //         // .execute();
        //         .enqueue(new Callback() {
        //             @Override
        //             public void onFailure(Call call, IOException e) {
        //                 e.printStackTrace();
        //                 System.out.println("失败咯");
        //             }
        //
        //             @Override
        //             public void onResponse(Call call, Response response) throws IOException {
        //                 System.out.println(response.headers().toString());
        //                 System.out.println(response.toString());
        //                 System.out.println(response.body().string());
        //                 System.out.println(Thread.currentThread().getId() + "\t成功咯");
        //             }
        //         });
        // System.out.println(Thread.currentThread().getId() + "\teeeyyy");
        // Thread.currentThread().join();

        Map<String, String> head = new HashMap<>();
        head.put("user-agent", "IDEA;chrome");
        for (int i = 0; i < 10; i++) {

            OkHttpUtils.getAsyc("https://www.baidu.com", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("访问百度失败咯");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    System.out.println(Thread.currentThread().getName() + "访问百度成功:" + response.code());
                }
            });


            OkHttpUtils.getAsyc("http://localhost:8999/oktest", null, head, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("失败咯");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    System.out.println(Thread.currentThread().getName() + "[客户端]:" + response.body().string());
                }
            });
        }
    }
}
