package kfsdk.alex.sun.testrxmodule.http;

import java.util.List;
import java.util.concurrent.TimeUnit;

import kfsdk.alex.sun.testrxmodule.module.HttpResult;
import kfsdk.alex.sun.testrxmodule.module.Subject;
import kfsdk.alex.sun.testrxmodule.service.MovieService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by kf on 2016/8/19.
 * 请求类
 */
public class HttpManager {
    private  static HttpManager httpManager;
    public static final String BASE_URL = "https://api.douban.com/v2/movie/";
    private MovieService movieService;
    private Retrofit retrofit;
    public static HttpManager getInstance(){
        if(httpManager==null){
            httpManager=new HttpManager();
        }
        return  httpManager;
    }

    public HttpManager() {
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient=new OkHttpClient.Builder();
        httpClient.connectTimeout(5, TimeUnit.SECONDS);
        httpClient.addInterceptor(interceptor);
//        httpClient.setL
        retrofit=new Retrofit.Builder().client(httpClient.build())
        .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL).build();
        movieService=retrofit.create(MovieService.class);
    }
    public  void getTopMovies(Subscriber<List<Subject>> subscriber,int start,int count){
        Observable observable =movieService.getTopMovie(start,count).map(new Func1<HttpResult<List<Subject>>, List<Subject>>() {
            @Override
            public List<Subject> call(HttpResult<List<Subject>> listHttpResult) {
                return listHttpResult.getSubjects();
            }
        });
        toSubscribe(observable,subscriber);

    }
    public <T> void toSubscribe(Observable<T> o,Subscriber<T> subscriber){
        o.subscribeOn(Schedulers.io())

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }
}
