package fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sherlookhohlmes.android.Appconfig;
import com.example.sherlookhohlmes.android.IntroActivity;
import com.example.sherlookhohlmes.android.R;
import adapter.MyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.SearchAdapter;
import model.Advertising;

/**
 * Created by SherlookHohlmes on 4/2/2018.
 */

public class HomeFragment extends Fragment {

    private View view;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Advertising> advertisings;
    private static Bundle mBundleRecyclerViewState;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private EditText edtSearch;
    private boolean flag = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("fragment-->", "loaded");

        view = inflater.inflate(R.layout.fragment_home, container, false);

//        String str = getArguments().getString("advertisings");

        try {
            advertisings = new ArrayList<>();

            for (int i = 0; i < Appconfig.allAdvertisings.length(); i++) {

                JSONObject adv = Appconfig.allAdvertisings.getJSONObject(i);
                Log.e("imageurl-->", adv.getString("image"));
                String city = "";
                if (adv.has("city"))
                    city = adv.getString("city");
                model.Advertising advertising = new Advertising(adv.getInt("id"),
                        adv.getInt("cat_id"),
                        adv.getString("title"),
                        adv.getString("location"),
                        adv.getString("description"),
                        adv.getString("image"),
                        adv.getString("email"),
                        adv.getString("phone"),
                        city);

                advertisings.add(advertising);

            }

            Log.e("advs-->", advertisings.size() + "");

        } catch (JSONException e) {
            Log.e("error: ", "parsingerror " + e);
        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new MyAdapter(advertisings, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        edtSearch = (EditText) view.findViewById(R.id.edt_search);
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getwithWord(v.getText().toString());

                    return true;
                }
                return false;
            }
        });

        return view;
    }

    private void getwithWord(String searchWord) {

        // Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        advertisings.clear();
        for (int i = 0; i < Appconfig.allAdvertisings.length(); i++) {

            try {
                JSONObject object = Appconfig.allAdvertisings.getJSONObject(i);
                if (object.getString("title").contains(searchWord)) {

                    model.Advertising advertising = new Advertising(object.getInt("id"),
                            object.getInt("cat_id"),
                            object.getString("title"),
                            object.getString("location"),
                            object.getString("description"),
                            object.getString("image"),
                            object.getString("email"),
                            object.getString("phone"),
                            object.getString("city"));
                    advertisings.add(advertising);
                }

                mAdapter = new SearchAdapter(advertisings, getActivity());
                mRecyclerView.setAdapter(mAdapter);

            } catch (JSONException e) {
                Log.e("parsing-->", "error: " + e);
            }

        }

    }

        @Override
    public void onPause() {
        super.onPause();

        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);

    }

    @Override
    public void onResume() {
        super.onResume();

        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    if (edtSearch.getText().toString().length() > 0) {

                        edtSearch.setText("");
                        getwithWord("");
                        Log.e("back--->", "OK");
                        return true;

                    }
                    else {

                        Log.e("backpressfrom-->", "homefrag");
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("می خواهید خارج شوید؟");
                        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //TODO
                                getActivity().finish();
                                dialog.dismiss();

                            }
                        });
                        builder.setNegativeButton("انصراف", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //TODO
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                        return true;

                    }

                    }

                return false;
            }
        });

    }

}
