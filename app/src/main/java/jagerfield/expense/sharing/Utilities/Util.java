package jagerfield.expense.sharing.Utilities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import jagerfield.expense.sharing.R;
import jagerfield.utilities.lib.AppUtilities;

public class Util
{
    public static final int NETWORK_SUCCESS_CODE = 200;
    public static final String BASE_URL = "http://10.0.0.11/expenses_sharing_app/";
    public static final String USERS_API = "users.php";
    public static final String EVENTS_API = "events.php";
    public static final int CHOOSE_PHOTO_CODE = 100;
    public static final String TAG_LIB = "TAG_LIB";
    public static final String ADMIN_JACKSON_STRING = "ADMIN_JACKSON_STRING";
    public static final String LOGIN_RESULT = "LOGIN_RESULT";
//    public static final String REGISTER = "REGISTER";
//    public static final String LOGIN = "LOGIN";

    public final static String[] PERMISSIONS_ARRAY = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.CAMERA
    };

    public static void launchFragment(AppCompatActivity activity, Fragment fragment)
    {

        AppUtilities.setSoftKeyboard(activity, false);

        /**
         *  Empty backstack
         *
         */
        FragmentManager fm = activity.getSupportFragmentManager();
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

    public static void showAlertMessage(Activity activity, String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
