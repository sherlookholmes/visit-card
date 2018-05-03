package fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.sherlookhohlmes.android.Appconfig;
import com.example.sherlookhohlmes.android.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.SearchAdapter;
import model.Advertising;
import ui_element.MyEditText;

/**
 * Created by SherlookHohlmes on 4/2/2018.
 */

public class SearchFragment extends Fragment {

    private View view;
    private AppCompatEditText searchBar;
    private RequestQueue MyRequestQueue;
    private ArrayList<Advertising> advertisings;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView imgSearch;
    private static Bundle mBundleRecyclerViewState;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private ImageView imgClose;
    private MyEditText edtSearch;
    private boolean flag = false;

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        //searchBar = (AppCompatEditText) view.findViewById(R.id.edt_search);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_search_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        getAllAdvertisings();
        //imgSearch = (ImageView) view.findViewById(R.id.img_search);
        imgClose = view.findViewById(R.id.img_close);
        edtSearch = (MyEditText) view.findViewById(R.id.edt_search);
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

        edtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK &&
                        keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    // do your stuff

                    if (!flag) {

                        edtSearch.setText("");
                        getwithWord("");
                        edtSearch.onKeyPreIme(i, keyEvent);
                        Log.e("back--->", "OK");
                        flag = true;
                        return true;
                    }
                    else
                    {

                        flag = false;
                        return false;

                    }
                }
                return false;
            }
        });

        return view;
    }

    private void getAllAdvertisings()
    {

        advertisings = new ArrayList<>();

        for (int i = 0; i < Appconfig.allAdvertisings.length(); i++) {

            try {

                JSONObject adv = Appconfig.allAdvertisings.getJSONObject(i);
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
            catch (JSONException e)
            {
                Log.e("parsing--->", "error: " + e);
            }

        }
        mAdapter = new SearchAdapter(advertisings, getActivity());
        mRecyclerView.setAdapter(mAdapter);

    }

    private void getwithWord(String searchWord) {

        // Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        advertisings.clear();
        for (int i = 0; i < Appconfig.allAdvertisings.length(); i ++)
        {

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
    public void onResume() {
        super.onResume();

        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
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

}