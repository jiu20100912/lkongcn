package cn.lkong.lkong.di.module;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import cn.lkong.lkong.api.ApiService;
import cn.lkong.lkong.api.net.RequestInterceptor;
import cn.lkong.lkong.constant.Constants;
import cn.lkong.lkong.core.SPUtils;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context providerContext() {
        return context;
    }

    @Provides
    @Singleton
    public OkHttpClient providerOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new RequestInterceptor())
                .build();
    }

    @Provides
    @Singleton
    public ApiService providerRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()
                .create(ApiService.class);
    }

    @Provides
    @Singleton
    public SPUtils providerSPUtils() {
        return new SPUtils(context);
    }

}
