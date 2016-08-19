package kfsdk.alex.sun.testrxmodule.service;

import java.util.List;

import kfsdk.alex.sun.testrxmodule.module.HttpResult;
import kfsdk.alex.sun.testrxmodule.module.Subject;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by kf on 2016/8/19.
 */
public interface MovieService {
    @GET("top250")
    Observable<HttpResult<List<Subject>>> getTopMovie(@Query("start") int start,@Query("count") int count);
}
