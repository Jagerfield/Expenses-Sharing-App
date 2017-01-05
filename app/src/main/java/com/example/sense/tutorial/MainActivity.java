package com.example.sense.tutorial;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import com.example.sense.tutorial.RetrofitApi.IRetrofit;
import com.example.sense.tutorial.UserDetailFragment.addUserFragment;
import com.example.sense.tutorial.UsersListFragment.RecordsListFragment;
import com.example.sense.tutorial.Utilities.C;

public class MainActivity extends AppCompatActivity {

    public IRetrofit restApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        C.launchFragment(this, new RecordsListFragment());

    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            C.launchFragment(this, new RecordsListFragment());
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, C.READ_CONTACT_PERMISSION_REQUEST_CODE);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == C.READ_CONTACT_PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            C.launchFragment(this, new RecordsListFragment());
        }
    }

    /**
     * Forward the back press to the current fragment
     *
     */
    @Override
    public void onBackPressed()
    {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (currentFragment==null)
        {
            return;
        }

        for (Fragment fragment : getSupportFragmentManager().getFragments())
        {
            if (fragment == currentFragment)
            {
                ((addUserFragment)fragment).onFragmentBackPress();
            }
        }

    }

}





