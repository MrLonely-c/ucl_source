package com.example.helloworld;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    public static void sendOKHttp3RequestPOST(final String url, final String jsonPara, final okhttp3.Callback callback) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody body = RequestBody.create(JSON, jsonPara);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                client.newCall(request).enqueue(callback);
            }
        }).start();
    }

    public static void sendOKHttp3RequestGET(final String url, final okhttp3.Callback callback, final Object... args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
//                RequestBody body = RequestBody.create(JSON, jsonstr);
                StringBuilder sb = new StringBuilder(url + "?");
                for (int i = 0; i < args.length; i += 2) {
                    sb.append("\"")
                            .append(args[i])
                            .append("\"")
                            .append(":")
                            .append("\"")
                            .append(args[i + 1])
                            .append("\"")
                            .append(",");
                }
                sb.setLength(sb.length() - 1);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(sb.toString())
                        .build();
                client.newCall(request).enqueue(callback);
            }
        }).start();
    }

}
