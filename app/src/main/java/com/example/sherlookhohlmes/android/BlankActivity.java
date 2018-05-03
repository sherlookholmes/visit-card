package com.example.sherlookhohlmes.android;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.NetworkError;
import com.android.volley.error.NoConnectionError;
import com.android.volley.error.ParseError;
import com.android.volley.error.ServerError;
import com.android.volley.error.TimeoutError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static java.util.jar.Pack200.Packer.PASS;

/**
 * Created by SherlookHohlmes on 4/13/2018.
 */

public class BlankActivity extends AppCompatActivity {

    private RequestQueue MyRequestQueue;
    private MaterialDialog pdialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (checkConnection()) {
            MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
                    builder.content(R.string.please_wait)
                    .progress(true, 0)
                    .cancelable(false);
                    pdialog = builder.build();
                    pdialog.show();
            getadvs();
        } else {
            Log.e("connection-->", "notconnect");
            showErrorDialog("لطفا اتصال به اینترنت را بررسی نمایید!");

        }

    }

    private void showErrorDialog(String message) {

        if (pdialog != null) {
            pdialog.cancel();
            pdialog.dismiss();
            Log.e("dialog-->", "OK");
        }

        new MaterialDialog.Builder(this)
                .content(message)
                .positiveText("باشه")
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        // TODO
                        finish();
                        dialog.dismiss();
                    }
                })
                .show();

    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }

    }

    public boolean checkConnection() {
        if (ContextCompat.checkSelfPermission(BlankActivity.this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 4);
            }

        }
        if (isOnline())
            return true;
        else
            return false;
    }



    private void getadvs() {

        //VolleyCallback volleyCallback = new vVolleyCallback();

        MyRequestQueue = Volley.newRequestQueue(BlankActivity.this);
        MyRequestQueue.getCache().clear();
        String url = Uri.parse(Appconfig.base_url + "api/getadvs").toString();
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response>>>>>>", response);
                    callbackSuccesResponse(response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("response>>>>>>", "Error" + error);
                if (error instanceof TimeoutError) {
                    showErrorDialog("اتمام زمان پاسخ! لطفا مجدد تلاش کنید");
                } else if (error instanceof ServerError) {
                    showErrorDialog("خطای سرور! لطفا مجدد تلاش کنید");
                } else if (error instanceof AuthFailureError) {
                    showErrorDialog("خطای احراز هویت! لطفا مجدد تلاش کنید");
                } else if (error instanceof NetworkError) {
                    showErrorDialog("خطای شبکه! لطفا مجدد تلاش کنید");
                } else if (error instanceof NoConnectionError) {
                    showErrorDialog("لطفا اتصال به اینترنت را بررسی نمایید!");
                } else if (error instanceof ParseError) {
                    showErrorDialog("خطای پارسینگ! لطفا مجدد تلاش کنید");
                }
                else
                    showErrorDialog("خطای شبکه! لطفا مجدد تلاش کنید");

            }
        });
        MyStringRequest.setShouldCache(false);
        MyRequestQueue.add(MyStringRequest);

    }

    private void callbackSuccesResponse(String mResponse)
    {

        try {
            Appconfig.allAdvertisings = new JSONArray(mResponse);
            Log.e("advertisings>>>>>>", Appconfig.allAdvertisings.length() + "");
            startActivity(new Intent(BlankActivity.this, IntroActivity.class));
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
