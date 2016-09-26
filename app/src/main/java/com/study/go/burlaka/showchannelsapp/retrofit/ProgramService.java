package com.study.go.burlaka.showchannelsapp.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Operator on 22.09.2016.
 */
public interface ProgramService {


    @GET("PROGRAM.json")
    Call<ResponseBody> getPrograms();

}
