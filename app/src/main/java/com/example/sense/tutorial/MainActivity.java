package com.example.sense.tutorial;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.example.sense.tutorial.RetrofitApi.IRetrofit;
import com.example.sense.tutorial.UserDetailFragment.AddUserFragment;
import com.example.sense.tutorial.UsersListFragment.UsersListFragment;
import com.example.sense.tutorial.Utilities.C;
import jagerfield.utilities.lib.AppUtilities;
import jagerfield.utilities.lib.PermissionsUtil.PermissionsUtil;
import jagerfield.utilities.lib.PermissionsUtil.Results.IGetPermissionResult;

public class MainActivity extends AppCompatActivity {

    public IRetrofit restApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions();
    }

    public void checkPermissions()
    {
        PermissionsUtil permissionsUtil = AppUtilities.getPermissionUtil(this);
        IGetPermissionResult result = permissionsUtil.getPermissionResults(C.PERMISSIONS_ARRAY);

        if (result.isGranted())
        {
            C.launchFragment(this, new UsersListFragment());
        }
        else if (!result.isGranted() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            //There are missing permissions ask for them
            permissionsUtil.requestPermissions(C.PERMISSIONS_ARRAY);
        }
        else if (!result.isGranted() && Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            //For SDK less than M, there are permissions missing in the manifest
            String missingPermissions = TextUtils.join(", ", result.getMissingInManifest_ForSdkBelowM()).trim();
            Toast.makeText(this, "Following permissions are missing : " + missingPermissions, Toast.LENGTH_LONG).show();
            Log.e(C.TAG_LIB, "Following permissions are missing : " + missingPermissions);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        PermissionsUtil permissionsUtil = AppUtilities.getPermissionUtil(this);

        if (requestCode == permissionsUtil.getPermissionsReqCodeId())
        {
            IGetPermissionResult result = null;
            result = permissionsUtil.getPermissionResults(C.PERMISSIONS_ARRAY);

            if (result == null) { return; }

            if (result.isGranted())
            {
                C.launchFragment(this, new UsersListFragment());
            }
            else
            {
                //For SDK >= M, there are permissions missing and you can get them.
                String deniedPermissions = TextUtils.join(", ", result.getUserDeniedPermissionsList()).trim();
                String neverAskAgainPermissions = TextUtils.join(", ", result.getNeverAskAgainPermissionsList()).trim();

                String missingPermissions = "";

                if (!deniedPermissions.isEmpty())
                {
                    if (!neverAskAgainPermissions.isEmpty())
                    {
                        neverAskAgainPermissions = ", " + neverAskAgainPermissions;
                    }

                    missingPermissions = deniedPermissions + neverAskAgainPermissions;
                }
                else
                {
                    missingPermissions = neverAskAgainPermissions;
                }

                Toast.makeText(this, "Following permissions are missing : " + missingPermissions, Toast.LENGTH_LONG).show();
                return;
            }
        }
    }


    /**
     * Forward the back press to the current fragment
     *
     */
    @Override
    public void onBackPressed()
    {
        for (Fragment fragment : getSupportFragmentManager().getFragments())
        {
            if (fragment.getClass().getSimpleName().equals("AddUserFragment"))
            {
                ((AddUserFragment)fragment).onFragmentBackPress();
            }
        }

    }

}





