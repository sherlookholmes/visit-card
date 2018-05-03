package com.example.sherlookhohlmes.android;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONArray;
import org.json.JSONException;

import fragment.CatFragment;
import fragment.HomeFragment;
import fragment.ProfileFragment;
import fragment.SearchFragment;

/**
 * Created by SherlookHohlmes on 4/1/2018.
 */

public class IntroActivity extends AppCompatActivity {

    public static BottomBar bottomNavigationView;
    private Fragment  profileFragment;
    private FragmentManager fm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.MyTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_intro);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        Appconfig.context = this;

        bottomNavigationView = (BottomBar) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setDefaultTab(R.id.navigation_home);

        Appconfig.catFragment = new CatFragment();
        Appconfig.searchFragment = new SearchFragment();
        Appconfig.homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();
        fm = getSupportFragmentManager();
        Appconfig.fm = fm;
        createFragments();

        //get advertisings
        //getadvs();

        bottomNavigationView.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {

                if (tabId == R.id.navigation_cats)
                    hideShowFragment(Appconfig.catFragment);
//                else if (tabId == R.id.navigation_search)
//                    hideShowFragment(Appconfig.searchFragment);
                else if (tabId == R.id.navigation_profile)
                    hideShowFragment(profileFragment);
                else if (tabId == R.id.navigation_home)
                    hideShowFragment(Appconfig.homeFragment);

            }

        });

    }

    private void createFragments() {
        addHideFragment(Appconfig.catFragment, "catFrag");
        addHideFragment(Appconfig.searchFragment, "searchFrag");
        addHideFragment(profileFragment, "profileFrag");
        fm.beginTransaction().add(R.id.frm_main_container, Appconfig.homeFragment, "homeFrag").commitAllowingStateLoss();
        Appconfig.active = Appconfig.homeFragment;
        Appconfig.defaultFragment = Appconfig.homeFragment;
    }
    private void addHideFragment(Fragment fragment, String TAG) {
        if (fragment != null)
        fm.beginTransaction().add(R.id.frm_main_container, fragment, TAG).hide(fragment).commit();
    }
    public static void hideShowFragment(Fragment show) {
        if (show != null) {
            for(Fragment hide:Appconfig.fm.getFragments()){
                Log.e("hide->->", hide + "");
                Appconfig.fm.beginTransaction().hide(hide).commit();
            }
            Appconfig.fm.beginTransaction().show(show).commit();
            Appconfig.active = show;
        }
    }
    public static void hideShowFragment() {
        Appconfig.fm.beginTransaction().hide(Appconfig.active).show(Appconfig.homeFragment).commit();
        Appconfig.active = Appconfig.homeFragment;
        Appconfig.defaultFragment = Appconfig.homeFragment;
    }

    @Override
    public void onBackPressed() {
        hideShowFragment(Appconfig.homeFragment);
        bottomNavigationView.selectTabWithId(R.id.navigation_home);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onCreate(savedInstanceState);
    }
}