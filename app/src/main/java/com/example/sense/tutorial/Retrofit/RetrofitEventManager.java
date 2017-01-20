package com.example.sense.tutorial.Retrofit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Window;
import com.example.sense.tutorial.Retrofit.Models.Event;
import com.example.sense.tutorial.Retrofit.Models.RestEventResponse;
import com.example.sense.tutorial.Utilities.Util;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

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

    public void getEvents(String action_type, String event_admin, String event_id, String from_date, String to_date, final IRetrofitCallback clientCallback)
    {

    }

//    private Map<String, RequestBody> formEventrequest(String action_type, String event_admin, String event_id, String from_date, String to_date)
//    {
//
//        Map<String, RequestBody> map = new HashMap<>();
//
//        if (event_admin!=null && !event_admin.isEmpty() && action_type!=null && !action_type.isEmpty())
//        {
//            /**
//             * Load name, email, mobile
//             */
//            map.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), action_type));
//            map.put("email", RequestBody.create(MediaType.parse("multipart/form-data"), event_admin));
//            map.put("mobile", RequestBody.create(MediaType.parse("multipart/form-data"), event_id));
//            map.put("mobile", RequestBody.create(MediaType.parse("multipart/form-data"), from_date));
//            map.put("mobile", RequestBody.create(MediaType.parse("multipart/form-data"), to_date));
//
//            /**
//             * Load image to map
//             */
////            if (entryValues.getImageFile() != null )
////            {
////                RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), entryValues.getImageFile());
////                map.put("image\"; filename=\"" + entryValues.getImageFile().getName() + "\"", fileBody);
////            }
//        }
//
//        return map;
//    }

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
            public void onResponse(Response<RestEventResponse> response, Retrofit retrofit) {
                pd.dismiss();

                if (response.code() == Util.NETWORK_SUCCESS_CODE)
                {

                    clientCallback.getEventResponse(response.body());
//                    if (response.body().getErrors().isEmpty())
//                    {
//                        ArrayList<Event> eventsList = response.body().getEvents();
//                        if (eventsList!=null)
//                        {
//                            clientCallback.eventsByUser(eventsList);
//                        }
//                    }
//                    else
//                    {
//                        ArrayList<String> list = response.body().getErrors();
//                    }
                }
                else
                {
                    int i = response.code();
                    String str = "";
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                pd.dismiss();
            }

        });
    }

    private IRetrofit getRetrofitObj()
    {
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.setConnectTimeout(10, TimeUnit.MINUTES);
        httpClient.setReadTimeout(10, TimeUnit.MINUTES);
        httpClient.setWriteTimeout(10, TimeUnit.MINUTES);

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
        void eventsByUser(ArrayList<Event> eventsByUser);
    }

}
