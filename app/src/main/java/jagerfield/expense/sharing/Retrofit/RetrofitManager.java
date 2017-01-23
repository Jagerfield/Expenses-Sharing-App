package jagerfield.expense.sharing.Retrofit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Window;

import jagerfield.expense.sharing.AddUserFragment.UserEntryEvaluation;
import jagerfield.expense.sharing.Utilities.Util;
import java.util.ArrayList;
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
            public void onResponse(Call<RestResponse> call, Response<RestResponse> response) {
                pd.dismiss();
                if (response.code() == Util.NETWORK_SUCCESS_CODE) {
                    if (response.body().getErrors().isEmpty()) {
                        ArrayList<User> users = response.body().getUsers();

                        clientCallback.getAllUsersList(users);
                    }
                }
            }

            @Override
            public void onFailure(Call<RestResponse> call, Throwable t) {
                t.printStackTrace();
                pd.dismiss();
            }
        });

    }

    private Map<String, RequestBody> getUSerValues(UserEntryEvaluation.EntryValues entryValues)
    {

        Map<String, RequestBody> map = new HashMap<>();

        if (entryValues instanceof UserEntryEvaluation.EntryValues)
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

    public void addUserToDatabase(UserEntryEvaluation.EntryValues entryValues, final IRetrofitCallback clientCallback)
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
            public void onResponse(Call<RestResponse> call, Response<RestResponse> response) {

                pd.dismiss();

                if (response.code() == Util.NETWORK_SUCCESS_CODE)
                {
                    if (response.body().getErrors().isEmpty())
                    {
                        ArrayList<User> addedUsers = response.body().getUsers();
                        if (addedUsers!=null)
                        {
                            clientCallback.getOnlyAddedUsersList(addedUsers);
                        }
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
            public void onFailure(Call<RestResponse> call, Throwable t) {
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
        void getAllUsersList(ArrayList<User> usersList);
        void getOnlyAddedUsersList(ArrayList<User> addedUsersList);
    }

}
