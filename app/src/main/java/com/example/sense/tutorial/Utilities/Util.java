package com.example.sense.tutorial.Utilities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.example.sense.tutorial.R;

public class Util
{
    public static final int NETWORK_SUCCESS_CODE = 200;
    public static final String BASE_URL = "http://10.0.0.11/expenses_sharing_app/";
    public static final String USERS_API = "users.php";
    public static final String EVENTS_API = "events.php";
    public static final int CHOOSE_PHOTO_CODE = 100;
    public static final String TAG_LIB = "TAG_LIB";
    public static final String APP_PREFERENCES = "APP_PREFERENCES";

    public final static String[] PERMISSIONS_ARRAY = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.CAMERA
    };

    public static void launchFragment(AppCompatActivity context, Fragment fragment)
    {
        if (!isNetworkAvailable(context))
        {
            Toast.makeText(context, "There is no internet connection", Toast.LENGTH_SHORT).show();
            return;
        }

        setSoftKeyBoard(context, false);

        /**
         *  Empty backstack
         *
         */
        FragmentManager fm = context.getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        if (count > 0)
        {
            FragmentManager.BackStackEntry entry = fm.getBackStackEntryAt(0);
            fm.popBackStack(entry.getId(), android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fm.executePendingTransactions();
        }

        /**
         *  Add the new fragment
         */
        String fragmentName = fragment.getClass().getSimpleName();
        fm.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .addToBackStack(fragmentName)
                .commit();
    }

    public static void setSoftKeyBoard(Activity activity, boolean mode) {
        if (activity == null) {
            return;
        }

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);

        if (mode) {
            inputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
        } else {
            if (inputMethodManager != null) {
                if (activity == null)
                    return;
                if (activity.getCurrentFocus() == null)
                    return;
                if (activity.getCurrentFocus().getWindowToken() == null)
                    return;
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }

        return false;
    }

//    public synchronized static boolean hasPermission(Activity activity, String permission)
//    {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
//        {
//            return true;
//        }
//
//        if (permission==null || permission.isEmpty())
//        {
//
//            return false;
//        }
//
//        String[] permissionsArray = {permission};
//
//        for (int i = 0; i < permissionsArray.length; i++)
//        {
//            if (activity.checkSelfPermission(permissionsArray[i]) == PackageManager.PERMISSION_DENIED)
//            {
//                Log.w(TAG_LIB, permission + " permission is missing.");
//                return false;
//            }
//            else
//            {
//                return true;
//            }
//        }
//
//        return false;
//    }

}
