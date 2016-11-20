package com.example.rlawnsgh78.chatapp;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by rlawn on 2016-11-20.
 */

public class HttpClient {

    public static final String BASE_URL = "http://127.0.0.1:3000/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static AsyncHttpClient getInstance() {
        return client;
    }


    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static Boolean post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        boolean check = client.post(getAbsoluteUrl(url), params, responseHandler).isFinished();
        if (check) {
            return true;
        } else {
            return false;
        }
    }

    public static void update(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.put(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void delete(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.delete(getAbsoluteUrl(url), params, responseHandler);
    }

    public static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
