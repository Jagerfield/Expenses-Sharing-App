package com.example.sense.tutorial.RetrofitApi;

import com.example.sense.tutorial.Utilities.C;
import com.example.sense.tutorial.RetrofitApi.API.Models.RestResponse;
import com.squareup.okhttp.RequestBody;
import java.util.Map;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PartMap;

public interface IRetrofit {
    @GET(C.USERS_API)
    Call<RestResponse> getUsers();

    @Multipart
    @POST(C.USERS_API)
    Call<RestResponse> addUser(@PartMap Map<String, RequestBody> params);

}

