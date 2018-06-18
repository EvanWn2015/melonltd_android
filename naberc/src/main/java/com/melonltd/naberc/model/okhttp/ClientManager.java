package com.melonltd.naberc.model.okhttp;

import android.annotation.SuppressLint;
import android.util.Log;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by evan on 2018/1/5.
 */

public class ClientManager {
    private static final String TAG = ClientManager.class.getSimpleName();
    private static final ConnectionPool CONNECTION_POOL = new ConnectionPool();
    private static final HttpLoggingInterceptor LOGGING = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectTimeout(25, TimeUnit.SECONDS)
            .readTimeout(25, TimeUnit.SECONDS)
            .writeTimeout(25, TimeUnit.SECONDS)
            .addInterceptor(LOGGING)
            .connectionPool(CONNECTION_POOL)
            .hostnameVerifier(new TrustAllHostnameVerifier())
            .sslSocketFactory(createSSLSocketFactory(), new TrustAllManager())
            .build();

    private static ClientManager CLIENT_MANAGER = new ClientManager();

    private final static String HEADER_KEY = "Authorization";
    private final static String PARAMETER ="data";

//    private static final MediaType X_WWW_FORM = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
//    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//    private static final MediaType TEXT = MediaType.parse("text/plain; charset=utf-8");

    public static ClientManager getInstance() {
        if (CLIENT_MANAGER == null) {
            CLIENT_MANAGER = new ClientManager();
        }
        return CLIENT_MANAGER;
    }

    public static Call post(String url, String data) {
        RequestBody formBody = new FormBody.Builder()
                .add(PARAMETER, data)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        return CLIENT.newCall(request);
    }

    public static Call post(String url, String token, String data) {
        RequestBody formBody = new FormBody.Builder()
                .add(PARAMETER, data)
                .build();

        Request request = new Request.Builder()
                .header(HEADER_KEY, token)
                .url(url)
                .post(formBody)
                .build();

        return CLIENT.newCall(request);
    }


//    public static Call get(String url) {
//        Request request = new Request.Builder()
////                .header(HEADER_KEY, Preference.headervalue)
////                .header("", "'")
////                .header("", "")
//                .url(url)
//                .get()
//                .build();
////        try {
////            Response response = CLIENT.newCall(request).execute();
////            return response;
////        } catch (IOException e) {
////            Log.e(TAG, "error", e);
////            return null;
////        }
//
//        return CLIENT.newCall(request);
//    }

//    public static Call get(URL url) {
//        Request request = new Request.Builder()
////                .header(HEADER_KEY, Preference.headervalue)
////                .header("", "'")
////                .header("", "")
//                .url(url)
//                .get()
//                .build();
//
//        return CLIENT.newCall(request);
//    }

//    public static Call get(HttpUrl url) {
//        Request request = new Request.Builder()
//                .header(HEADER_KEY, "Bearer f181ab6c-b320-48c1-928c-78282b02ad2d")
//                .url(url)
//                .get()
//                .build();
//
//        return CLIENT.newCall(request);
//    }

//    private static Response post(String url, String json) {
//        RequestBody body = RequestBody.create(JSON, json);
//
//        Request request = new Request.Builder()
////                .header(HEADER_KEY, Preference.headervalue)
//                .url(url)
//                .post(body)
//                .build();
//        try {
//            Response response = CLIENT.newCall(request).execute();
//            return response;
//        } catch (IOException e) {
//            Log.e(TAG, "error", e);
//            return null;
//        }
//    }

//    private static Response post(String url, Object object) {
//        RequestBody body = RequestBody.create(JSON, Tools.GSON.toJson(object));
//        Request request = new Request.Builder()
////                .header(HEADER_KEY, Preference.headervalue)
//                .url(url)
//                .post(body)
//                .build();
//        try {
//            Response response = CLIENT.newCall(request).execute();
//            return response;
//        } catch (IOException e) {
//            Log.e(TAG, "error", e);
//            return null;
//        }
//    }



//    public static Call put(String url, String json) {
//        RequestBody body = RequestBody.create(JSON, json);
//        Request request = new Request.Builder()
////                .header(HEADER_KEY, Preference.headervalue)
//                .url(url)
//                .put(body)
//                .build();
//
//        return CLIENT.newCall(request);
////        call.enqueue(callback);
////        try {
////            Response response = CLIENT.newCall(request).execute();
////            return response;
////        } catch (IOException e) {
////            Log.e(TAG, "error", e);
////            return null;
////        }
//    }
//
//    public static Response put(String url, Object object) {
//        RequestBody body = RequestBody.create(JSON, Tools.GSON.toJson(object));
//        Request request = new Request.Builder()
////                .header(HEADER_KEY, Preference.headervalue)
//                .url(url)
//                .put(body)
//                .build();
//        try {
//            Response response = CLIENT.newCall(request).execute();
//            return response;
//        } catch (IOException e) {
//            Log.e(TAG, "error", e);
//            return null;
//        }
//    }


//    public static Response delete(String url) {
//        Request request = new Request.Builder()
////                .header(HEADER_KEY, Preference.headervalue)
//                .url(url)
//                .delete()
//                .build();
//        try {
//            Response response = CLIENT.newCall(request).execute();
//            return response;
//        } catch (IOException e) {
//            Log.e(TAG, "error", e);
//            return null;
//        }
//    }

    @SuppressLint("TrulyRandom")
    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sSLSocketFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()}, new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
            Log.e(TAG, "error", e);
            e.printStackTrace();
        }
        return sSLSocketFactory;
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }

        public static TrustAllHostnameVerifier newInstance() {
            return new TrustAllHostnameVerifier();
        }
    }

    private static class TrustAllManager implements X509TrustManager {

        public static TrustAllManager newInstance() {
            return new TrustAllManager();
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

}
