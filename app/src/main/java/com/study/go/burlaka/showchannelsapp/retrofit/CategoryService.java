package com.study.go.burlaka.showchannelsapp.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Operator on 24.09.2016.
 */
public interface CategoryService {

    @GET("CATEGORY.json")
    Call<ResponseBody> getCategory();

}
