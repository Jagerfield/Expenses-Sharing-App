package com.example.sense.tutorial.Retrofit;

import com.example.sense.tutorial.Utilities.Util;
import com.squareup.okhttp.RequestBody;
import java.util.Map;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PartMap;
import retrofit2.http.Path;

public interface IRetrofit {
    @GET(Util.USERS_API)
    Call<RestResponse> getUsers();

    @Multipart
    @POST(Util.USERS_API)
    Call<RestResponse> addUser(@PartMap Map<String, RequestBody> params);


//    @Multipart
//    @GET(Util.USERS_API + "{id}")
//    Call<RestResponse> events(@Path("id") String id);

    @Multipart
    @POST(Util.EVENTS_API)
    Call<RestResponse> events(String action_type, @PartMap Map<String, RequestBody> params);
}

