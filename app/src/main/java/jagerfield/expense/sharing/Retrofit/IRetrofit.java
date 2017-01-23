package jagerfield.expense.sharing.Retrofit;

import jagerfield.expense.sharing.Retrofit.Models.RestEventResponse;
import jagerfield.expense.sharing.Utilities.Util;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface IRetrofit
{
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
    Call<RestEventResponse> eventsApi(@PartMap Map<String, RequestBody> params);
}

