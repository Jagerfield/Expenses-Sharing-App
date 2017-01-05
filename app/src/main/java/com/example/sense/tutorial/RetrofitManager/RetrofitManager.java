package com.example.sense.tutorial.RetrofitManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Window;
import com.example.sense.tutorial.RetrofitApi.API.Models.RestResponse;
import com.example.sense.tutorial.RetrofitApi.API.Models.User;
import com.example.sense.tutorial.RetrofitApi.IRetrofit;
import com.example.sense.tutorial.Utilities.C;
import com.example.sense.tutorial.Utilities.UserEntry;
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

public class RetrofitManager
{
    private Activity activity;
    private IRetrofit restApi;
    private IRetrofitCallback clientCallback;

    public RetrofitManager(Activity activity)
    {
        this.activity = activity;
        restApi = getRetrofitObj();
    }

    public static RetrofitManager getNewInstance(Activity activity)
    {
        return new RetrofitManager(activity);
    }

    public void getUsersFromDatabase(final IRetrofitCallback clientCallback)
    {
        final ProgressDialog pd = getProgressDialog();

        Call<RestResponse> getUsers = restApi.getUsers();

        getUsers.enqueue(new Callback<RestResponse>() {
            @Override
            public void onResponse(Response<RestResponse> response, Retrofit retrofit) {
                pd.dismiss();
                if (response.code() == C.NETWORK_SUCCESS_CODE) {
                    if (response.body().getErrors().isEmpty()) {
                        ArrayList<User> users = response.body().getUsers();

                        clientCallback.getAllUsersList(users);
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

    private Map<String, RequestBody> getUSerValues(UserEntry.EntryValues entryValues)
    {

        Map<String, RequestBody> map = new HashMap<>();

        if (entryValues instanceof UserEntry.EntryValues)
        {
            /**
             * Load name, email, mobile
             */
            map.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), entryValues.getName()));
            map.put("email", RequestBody.create(MediaType.parse("multipart/form-data"), entryValues.getEmail()));
            map.put("mobile", RequestBody.create(MediaType.parse("multipart/form-data"), entryValues.getNumber()));

            /**
             * Load image to map
             */
            if (entryValues.getImageFile() != null )
            {
                RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), entryValues.getImageFile());
                map.put("image\"; filename=\"" + entryValues.getImageFile().getName() + "\"", fileBody);
            }
        }

        return map;
    }

    public void addUserToDatabase(UserEntry.EntryValues entryValues, final IRetrofitCallback clientCallback)
    {
        final ProgressDialog pd = getProgressDialog();
        Map<String, RequestBody> map = getUSerValues(entryValues);

        if(map==null)
        {
            return;
        }

        Call<RestResponse> addUser = restApi.addUser(map);

        addUser.enqueue(new Callback<RestResponse>() {
            @Override
            public void onResponse(Response<RestResponse> response, Retrofit retrofit) {
                pd.dismiss();

                if (response.code() == C.NETWORK_SUCCESS_CODE)
                {
                    if (response.body().getErrors().isEmpty())
                    {
                        ArrayList<User> addedUsers = response.body().getUsers();
                        clientCallback.getOnlyAddedUsersList(addedUsers);
                    }
                    else
                    {
                        ArrayList<String> list = response.body().getErrors();
                    }
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

    public interface IRetrofitCallback
    {
        void getAllUsersList(ArrayList<User> usersList);
        void getOnlyAddedUsersList(ArrayList<User> addedUsersList);
    }

}
