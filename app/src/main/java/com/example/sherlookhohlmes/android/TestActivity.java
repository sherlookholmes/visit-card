package com.example.sherlookhohlmes.android;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
 * Created by SherlookHohlmes on 4/3/2018.
 */

public class TestActivity extends AppCompatActivity {

    private BottomBar bottomNavigationView;
    private Fragment searchFragment, profileFragment;
    private FragmentManager fm;
    private RequestQueue MyRequestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_intro);

        Appconfig.context = this;

        bottomNavigationView = (BottomBar) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setDefaultTab(R.id.navigation_home);

        Appconfig.catFragment = new CatFragment();
        searchFragment = new SearchFragment();
        profileFragment = new ProfileFragment();
        Appconfig.homeFragment = new HomeFragment();
        fm = getSupportFragmentManager();
        Appconfig.fm = fm;
        createFragments();

        //get advertisings
        getadvs();

        bottomNavigationView.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {

                if (tabId == R.id.navigation_cats)
                    hideShowFragment(Appconfig.catFragment);
//                else if (tabId == R.id.navigation_search)
//                    hideShowFragment(searchFragment);
                else if (tabId == R.id.navigation_profile)
                    hideShowFragment(profileFragment);
//                else if (tabId == R.id.navigation_home)
//                    hideShowFragment(Appconfig.homeFragment);

                }

        });

    }

    private void createFragments() {
        addHideFragment(Appconfig.catFragment, "catFrag");
        addHideFragment(searchFragment, "searchFrag");
        addHideFragment(profileFragment, "profileFrag");
    }
    private void addHideFragment(Fragment fragment, String TAG) {
        fm.beginTransaction().add(R.id.frm_main_container, fragment, TAG).hide(fragment).commit();
    }
    public static void hideShowFragment(Fragment show) {
        Appconfig.fm.beginTransaction().hide(Appconfig.active).show(show).commit();
        Appconfig.active = show;
    }

    private void getadvs()
    {

        MyRequestQueue = Volley.newRequestQueue(TestActivity.this);
        String url = Uri.parse(Appconfig.base_url + "api/getadvs").toString();
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray advs = new JSONArray(response);
                    Log.e("advertisings>>>>>>", advs.length() + "");
                    //set constructor for home fragment
                    Bundle bundle = new Bundle();
                    bundle.putString("advertisings", advs + "");
                    bundle.putString("prevFrag", "introAct");
                    //set Fragmentclass Arguments
                    Appconfig.homeFragment.setArguments(bundle);

                    fm.beginTransaction().add(R.id.frm_main_container, Appconfig.homeFragment).commit();
                    Appconfig.active = Appconfig.homeFragment;
                    Appconfig.defaultFragment = Appconfig.homeFragment;

                } catch (JSONException e) {
                    Log.e("response>>>>>>", "parseError: " + e);
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("response>>>>>>", "Error" + error);

            }
        });
        MyRequestQueue.add(MyStringRequest);

    }

}
