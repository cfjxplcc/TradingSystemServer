package com.fjnu.tradingsysrem.common.retrofit;

import com.fjnu.tradingsysrem.common.gson.adapter.DoubleDefault0Adapter;
import com.fjnu.tradingsysrem.common.gson.adapter.FloatDefault0Adapter;
import com.fjnu.tradingsysrem.common.gson.adapter.IntegerDefault0Adapter;
import com.fjnu.tradingsysrem.common.retrofit.factory.EmptyJsonLenientConverterFactory;
import com.fjnu.tradingsysrem.common.retrofit.factory.NobodyConverterFactory;
import com.fjnu.tradingsysrem.common.retrofit.ssl.IgnoreHttpsCertificateSSLSocketClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

/**
 * Created by LCC on 2018/4/3.
 */
public class RetrofitManager {

    /** 默认连接超时 */
    public static final int DEFAULT_CONNECT_TIMEOUT = 30;
    /** 默认读超时 */
    public static final int DEFAULT_READ_TIMEOUT = 30;
    /** 默认写超时 */
    public static final int DEFAULT_WRITE_TIMEOUT = 20;

    private RetrofitManager() {
    }

  /*  public static final RetrofitManager getInstance() {
        return InnerHolder.mInstance;
    }*/

    /**
     * 获取默认Retrofit对象
     *
     * @param serverUrl 服务器地址
     * @return
     */
    public static Retrofit getRetrofit(String serverUrl) {
        return getRetrofit(serverUrl, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT, DEFAULT_WRITE_TIMEOUT);
    }

    /**
     * 获取自定义Retrofit对象
     *
     * @param serverUrl      服务器地址 必须要以'/'结尾
     * @param connectTimeout 单位：秒
     * @param readTimeout    单位：秒
     * @param writeTimeout   单位：秒
     * @return
     */
    public static Retrofit getRetrofit(String serverUrl, long connectTimeout, long readTimeout, long writeTimeout) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .sslSocketFactory(IgnoreHttpsCertificateSSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(IgnoreHttpsCertificateSSLSocketClient.getHostnameVerifier())
//                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(8888))) // 设置代理
                .build();

        Gson gson = getGson();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(serverUrl)
                .addConverterFactory(NobodyConverterFactory.create())
                .addConverterFactory(new EmptyJsonLenientConverterFactory(GsonConverterFactory.create(gson)))
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    public static Gson getGson() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(Float.class, new FloatDefault0Adapter())
                .registerTypeAdapter(float.class, new FloatDefault0Adapter())
                .registerTypeAdapter(Timestamp.class, (JsonDeserializer<Timestamp>) (json, typeOfT, context) -> new Timestamp(json.getAsJsonPrimitive().getAsLong()))
                .registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()))
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
//                .serializeNulls()
                .create();
        return gson;
    }

    public Retrofit resetUrl(Retrofit retrofit, String serviceUrl) {
        return retrofit.newBuilder().baseUrl(serviceUrl).build();
    }

   /* private static final class InnerHolder {
        private static final RetrofitManager mInstance = new RetrofitManager();
    }*/
}
