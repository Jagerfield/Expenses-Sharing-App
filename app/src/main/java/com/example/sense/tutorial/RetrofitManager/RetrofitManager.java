package com.example.sense.tutorial.RetrofitManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Window;

import com.example.sense.tutorial.RetrofitApi.API.Models.RestResponse;
import com.example.sense.tutorial.RetrofitApi.API.Models.User;
import com.example.sense.tutorial.RetrofitApi.IRetrofit;
import com.example.sense.tutorial.Utilities.C;
import com.example.sense.tutorial.Utilities.Validation;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import org.greenrobot.eventbus.EventBus;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RetrofitManager {
    private Activity activity;
    private IRetrofit restApi;

    public RetrofitManager(Activity activity) {
        this.activity = activity;
        restApi = getRetrofitObj();
    }

    public void getRecordsFromDatabase() {
        final ProgressDialog pd = getProgressDialog();

        Call<RestResponse> getUsers = restApi.getUsers();

        getUsers.enqueue(new Callback<RestResponse>() {
            @Override
            public void onResponse(Response<RestResponse> response, Retrofit retrofit) {
                pd.dismiss();
                if (response.code() == C.NETWORK_SUCCESS_CODE) {
                    if (response.body().getErrors().isEmpty()) {
                        ArrayList<User> users = response.body().getUsers();
                        EventBus.getDefault().post(users);
                        String str = "";
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                pd.dismiss();
            }
        });
    }

    private Map<String, RequestBody> getRecord(Validation.Response record, File imageFile)
    {

        Map<String, RequestBody> map = new HashMap<>();

        /**
         * Load name, email, mobile
         */
        map.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), record.getName()));
        map.put("email", RequestBody.create(MediaType.parse("multipart/form-data"), record.getEmail()));
        map.put("mobile", RequestBody.create(MediaType.parse("multipart/form-data"), record.getNumber()));

        /**
         * Load image to map
         */
        if (imageFile != null)
        {
            RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
            map.put("image\"; filename=\"" + imageFile.getName() + "\"", fileBody);
        }

        return map;
    }

    public void addRecordToDatabase(Validation.Response record, File imageFile)
    {
        final ProgressDialog pd = getProgressDialog();
        final ArrayList<String> errors = new ArrayList<String>();

        Map<String, RequestBody> map = getRecord(record, imageFile);

        if(map==null)
        {
            return;
        }

        Call<RestResponse> addUser = restApi.addUser(map);

        addUser.enqueue(new Callback<RestResponse>() {
            @Override
            public void onResponse(Response<RestResponse> response, Retrofit retrofit) {
                pd.dismiss();

                if (response.code() == C.NETWORK_SUCCESS_CODE) {
                    if (response.body().getErrors().isEmpty()) {
                        ArrayList<User> addedUsers = response.body().getUsers();

                        EventBus.getDefault().post(addedUsers);
                        String str = "";
                    } else {
                        ArrayList<String> list = response.body().getErrors();
                        String str = "";
                    }

                } else {
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


    private IRetrofit getRetrofitObj() {
        OkHttpClient httpClient = new OkHttpClient();

        httpClient.setConnectTimeout(5, TimeUnit.MINUTES);
        httpClient.setReadTimeout(5, TimeUnit.MINUTES);
        httpClient.setWriteTimeout(5, TimeUnit.MINUTES);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(C.BASE_URL)
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


}
