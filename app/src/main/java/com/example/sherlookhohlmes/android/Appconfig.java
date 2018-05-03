package com.example.sherlookhohlmes.android;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import org.json.JSONArray;

/**
 * Created by SherlookHohlmes on 3/24/2018.
 */

public class Appconfig {

    //genymotion id --> http://10.0.3.2:8000/
    public static String base_url = "http://mprogramming.ir/";
    public static FragmentManager fm;
    public static Fragment active, defaultFragment, catFragment, homeFragment, catListFragment, searchFragment;
    public static Context context;
    public static JSONArray allAdvertisings;

}
