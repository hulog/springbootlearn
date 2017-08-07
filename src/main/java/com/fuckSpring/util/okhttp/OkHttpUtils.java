package com.fuckSpring.util.okhttp;

import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class OkHttpUtils {

    // 使用默认配置
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS).build();

    private OkHttpUtils() {
    }


    // 1. 组装方法
    // switch (method) {
    //     case Method.GET: {
    //         builder.get();
    //         if (paramsMap != null && paramsMap.size() > 0) {
    //             StringBuilder sb = new StringBuilder();
    //             for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
    //                 sb.append()
    //             }
    //         }
    //     }
    //     case Method.POST: {
    //         FormBody.Builder formBuild = new FormBody.Builder();
    //         for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
    //             formBuild.add(entry.getKey(), entry.getValue());
    //         }
    //         FormBody body = formBuild.build();
    //         builder.post(body);
    //     }
    //     default: {
    //         builder.get();
    //     }
    // }
    // // 2. 组装URL
    // builder.url(url);
    // // 3. 组装header
    // if (headersMap != null || headersMap.size() > 0) {
    //     for (Map.Entry<String, String> entry : headersMap.entrySet()) {
    //         builder.addHeader(entry.getKey(), entry.getValue());
    //     }
    // }
    // return builder.build();
// }


    public static void getAsync(String url, Callback callback) {
        getAsync(url, null, null, callback);
    }

    public static void getAsync(String url, Map<String, String> paramsMap, Callback callback) {
        getAsync(url, paramsMap, null, callback);
    }

    public static void getAsync(String url, Map<String, String> paramsMap, Map<String, String> headersMap, Callback callback) {

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

    private static Request makeGetRequest(String url, Map<String, String> paramsMap, Map<String, String> headersMap) {

        Request.Builder builder = new Request.Builder();

        // 1. 确定请求方式GET
        builder.get();

        // 2. 组装URL
        if (null == paramsMap && paramsMap.size() < 1) {
            builder.url(url);
        } else {
            StringBuilder sb = new StringBuilder(url);
            sb.append("?");
            for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
                sb.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }
            builder.url(sb.substring(0, sb.length() - 1));
        }

        // 3. 组装Header
        if (headersMap != null && headersMap.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : headersMap.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        // 4. 组装完毕返回
        return builder.build();
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

    public static Response get(String url) throws IOException {
        return get(url, null, null);
    }

    /**
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

            OkHttpUtils.getAsync("https://www.baidu.com", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("访问百度失败咯");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    System.out.println(Thread.currentThread().getName() + "访问百度成功:"+response.code());
                }
            });


            OkHttpUtils.getAsync("http://localhost:8999/oktest", null, head, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("失败咯");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    System.out.println(Thread.currentThread().getName()+"[客户端]:" + response.body().string());
                }
            });
        }
    }
}
