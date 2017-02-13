package jagerfield.expense.sharing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import jagerfield.expense.sharing.Models.Admin;
import jagerfield.expense.sharing.Utilities.PreferenceUtil;
import jagerfield.expense.sharing.Utilities.Util;
import jagerfield.utilities.lib.AppUtilities;

public class LoginActivity extends AppCompatActivity
{
    private static final int RC_SIGN_IN = 1100;
    private SignInButton signInButton;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeGoogleSigninApi();

        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                signIn();
            }
        });
    }

    private void initializeGoogleSigninApi()
    {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener()
                {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
                    {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void signIn()
    {
//        if (!AppUtilities.getNetworkUtil().hasInternetConnection(this))
//        {
//            Util.showAlertMessage(this, "Attention", "There is no internet connection");
//            return;
//        }
        
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result)
    {
        Log.d(Util.TAG_LIB, "handleSignInResult: " + result.isSuccess());

        if (result.isSuccess())
        {
            Admin admin = new Admin();
            GoogleSignInAccount signInAccount = result.getSignInAccount();
            admin.setGmail(signInAccount.getEmail());
            admin.setAdminName(signInAccount.getGivenName() + " " + signInAccount.getFamilyName());
            admin.setAdminImage(signInAccount.getPhotoUrl().toString());
            admin.setUdId(AppUtilities.getDeviceUtil().getAndroidId(this));

            String jsonStr = admin.convertObjToJsonStr(admin);

            PreferenceUtil.setString(this, Util.ADMIN_JACKSON_STRING, jsonStr);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else
        {

        }
    }


}

