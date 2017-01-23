package jagerfield.expense.sharing.Retrofit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Window;

import jagerfield.expense.sharing.Retrofit.Models.RestEventResponse;
import jagerfield.expense.sharing.Utilities.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitEventManager
{
    private Activity activity;
    private IRetrofit restApi;
    private IRetrofitCallback clientCallback;

    public RetrofitEventManager(Activity activity)
    {
        this.activity = activity;
        restApi = getRetrofitObj();
    }

    public static RetrofitEventManager newInstance(Activity activity)
    {
        return new RetrofitEventManager(activity);
    }

    public void getEventsbyUser(String action_type, String event_admin, String event_id, String from_date, String to_date, final IRetrofitCallback clientCallback)
    {
        final ProgressDialog pd = getProgressDialog();

        Map<String, RequestBody> map = new HashMap<>();

        if (event_admin!=null && !event_admin.isEmpty() && action_type!=null && !action_type.isEmpty())
        {
            map.put("action_type", RequestBody.create(MediaType.parse("multipart/form-data"), action_type));
            map.put("event_admin", RequestBody.create(MediaType.parse("multipart/form-data"), event_admin));
            map.put("event_id", RequestBody.create(MediaType.parse("multipart/form-data"), event_id));
            map.put("from_date", RequestBody.create(MediaType.parse("multipart/form-data"), from_date));
            map.put("to_date", RequestBody.create(MediaType.parse("multipart/form-data"), to_date));
        }

        Call<RestEventResponse> getTarget = restApi.eventsApi(map);

        getTarget.enqueue(new Callback<RestEventResponse>() {
            @Override
            public void onResponse(Call<RestEventResponse> call, Response<RestEventResponse> response) {

                pd.dismiss();

                if (response.code() == Util.NETWORK_SUCCESS_CODE)
                {
                    clientCallback.getEventResponse(response.body());
                }

            }

            @Override
            public void onFailure(Call<RestEventResponse> call, Throwable t) {

                t.printStackTrace();
                pd.dismiss();
            }

        });
    }

    private IRetrofit getRetrofitObj()
    {
        // For logging request & response (Optional)
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Util.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        IRetrofit iRetrofit = retrofit.create(IRetrofit.class);

        return iRetrofit;
    }

    private ProgressDialog getProgressDialog() {
        final ProgressDialog pd = new ProgressDialog(activity);
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();
        return pd;
    }

    public interface IRetrofitCallback
    {
        void getEventResponse(RestEventResponse restEventResponse);
    }

}
