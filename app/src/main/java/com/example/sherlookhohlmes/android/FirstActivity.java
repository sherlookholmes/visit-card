package com.example.sherlookhohlmes.android;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import model.Advertising;

/**
 * Created by SherlookHohlmes on 3/27/2018.
 */

public class FirstActivity extends AppCompatActivity {

    private RequestQueue MyRequestQueue;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<model.Advertising> advertisings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        MyRequestQueue = Volley.newRequestQueue(FirstActivity.this);
        String url = Uri.parse(Appconfig.base_url + "api/getadvs").toString();
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    advertisings = new ArrayList<>();
                    JSONArray advs = new JSONArray(response);
                    for (int i = 0; i < advs.length(); i++) {

                        JSONObject adv = advs.getJSONObject(i);
                        model.Advertising advertising = new Advertising(adv.getInt("id"),
                                adv.getInt("cat_id"),
                                adv.getString("title"),
                                adv.getString("location"),
                                adv.getString("description"),
                                adv.getString("image"),
                                adv.getString("email"),
                                adv.getString("phone"),
                                adv.getString("city"));

                        advertisings.add(advertising);

                    }
                    Log.e("advertisings>>>>>>", advertisings.size() + "");
                    // specify an adapter (see also next example)
                    //mAdapter = new MyAdapter(advertisings, FirstActivity.this);
                    mRecyclerView.setAdapter(mAdapter);

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
