package jagerfield.expense.sharing;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.gms.common.api.GoogleApiClient;
import jagerfield.expense.sharing.Models.Admin;
import jagerfield.expense.sharing.Retrofit.IRetrofit;
import jagerfield.expense.sharing.AddUserFragment.AddUserFragment;
import jagerfield.expense.sharing.UsersListFragment.UsersListFragment;
import jagerfield.expense.sharing.Utilities.PreferenceUtil;
import jagerfield.expense.sharing.Utilities.Util;
import jagerfield.utilities.lib.AppUtilities;
import jagerfield.utilities.lib.PermissionsUtil.GuiDialog.PermissionsManager;
import jagerfield.utilities.lib.PermissionsUtil.PermissionsUtil;
import jagerfield.utilities.lib.PermissionsUtil.Results.IGetPermissionResult;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.fragment_container);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        PreferenceUtil.clearAllPreferences(this);

        if (isRegistered())
        {
            //Continue with the program
        }
        else
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }


//        RetrofitEventManager retrofitEventManager = RetrofitEventManager.newInstance(this);
//        retrofitEventManager.getEventsbyUser("GET_EVENTS", "5", "4", "", "", new RetrofitEventManager.IRetrofitCallback() {
//            @Override
//            public void getEventResponse(RestEventResponse restEventResponse)
//            {
//                RestEventResponse response = restEventResponse;
//                String str = "";
//            }
//
//            @Override
//            public void eventsByUser(ArrayList<Event> eventsByUser)
//            {
//                ArrayList<Event> eventsList = eventsByUser;
//                String str = "";
//            }
//        });

//        checkPermissions();
    }

    private boolean isRegistered()
    {
        String jacksonString = PreferenceUtil.getString(this, Util.ADMIN_JACKSON_STRING, "");
        Admin admin = null;

        if (jacksonString!=null && !jacksonString.isEmpty())
        {
            admin = Admin.convertJsonStrToObj(jacksonString);
            if (admin!=null)
            {
                return admin.checkObjectValues();
            }
        }

        return false;
    }

    public void checkPermissions()
    {
        PermissionsUtil permissionsUtil = AppUtilities.getPermissionUtil(this);
        IGetPermissionResult result = permissionsUtil.getPermissionResults(Util.PERMISSIONS_ARRAY);

        if (result.isGranted())
        {
            Util.launchFragment(this, new UsersListFragment());
        }
        else if (!result.isGranted() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            //There are missing permissions ask for them
            permissionsUtil.requestPermissions(Util.PERMISSIONS_ARRAY);
        }
        else if (!result.isGranted() && Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            //For SDK less than M, there are permissions missing in the manifest
            String missingPermissions = TextUtils.join(", ", result.getMissingInManifest_ForSdkBelowM()).trim();
            Toast.makeText(this, "Following permissions are missing : " + missingPermissions, Toast.LENGTH_LONG).show();
            Log.e(Util.TAG_LIB, "Following permissions are missing : " + missingPermissions);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }


    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        PermissionsUtil permissionsUtil = AppUtilities.getPermissionUtil(this);

        if (requestCode == permissionsUtil.getPermissionsReqCodeId())
        {
            IGetPermissionResult result = null;
            result = permissionsUtil.getPermissionResults(permissions);

            if (result.isGranted())
            {
                Util.launchFragment(this, new UsersListFragment());
                return;
            }

            final AppCompatActivity activity = this;

            PermissionsManager.getNewInstance(activity, result, permissions, new PermissionsManager.PermissionsManagerCallback()
            {
                @Override
                public void onPermissionsGranted(IGetPermissionResult result) {

                    /**
                     * Member accepted all requested permissions
                     */

                    Util.launchFragment(activity, new UsersListFragment());
                }

                @Override
                public void onPermissionsMissing(IGetPermissionResult result)
                {
                    //Write your code here
                    Toast.makeText(MainActivity.this, "Member didn't accept all permissions", Toast.LENGTH_LONG).show();
                }
            });
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.fragment_container);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.fragment_container);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}




