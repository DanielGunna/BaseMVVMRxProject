package br.com.gunna.basemvvmrxproject.androidapp.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import br.com.gunna.basemvvmrxproject.androidapp.BuildConfig;
import br.com.gunna.basemvvmrxproject.androidapp.base.RxErrorHandlingCallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Daniel on 06/12/17.
 */

public abstract class AppService{
    private static IAppService sInstance;

    public static IAppService getInstance(){
        if(sInstance == null)
            createInstance();
        return sInstance;
    }

    private static void createInstance() {
        final OkHttpClient client = getServiceBuilder();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        final Retrofit retrofitInstance = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .build();

        sInstance = retrofitInstance.create(IAppService.class);
    }

    private static OkHttpClient getServiceBuilder(){
        final OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2,TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES);
        addHeaders(builder);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logInterceptor);
        }

        return builder.build();
    }

    private static void addHeaders(final OkHttpClient.Builder builder) {
        builder.addInterceptor(
                chain -> {
                    Request request = chain.request();

                    Request.Builder newRequest = request.newBuilder()
                            .method(request.method(), request.body());

                    //TODO  : add request headers
                    newRequest.addHeader("Accept", "application/json; q=0.5");

                    return chain.proceed(newRequest.build());
                }
        );
    }
}
