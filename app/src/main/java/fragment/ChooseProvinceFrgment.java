package fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.example.sherlookhohlmes.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import model.Province;

/**
 * Created by SherlookHohlmes on 9/12/2017.
 */

public class ChooseProvinceFrgment extends DialogFragment {

    private View view;
    private ListView listView;
    private ArrayList<Province> provincesList;
    private JSONArray provinces;
    private ArrayList<String> finalProvinces = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_chooseprovince_frgment, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        listView = (ListView) view.findViewById(R.id.category_listView);

        provincesList = new ArrayList<>();

        try {

            provinces = new JSONArray(loadJSONFromAsset("province.json"));

            for (int i = 0; i < provinces.length(); i ++)
            {

                JSONObject object = provinces.getJSONObject(i);
                Province province = new Province(object.getString("id"), object.getString("name"));
                provincesList.add(province);

                finalProvinces.add(province.getName());

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item, finalProvinces);
        listView.setAdapter(adapter);

        Log.e("list>>>", finalProvinces.size() + "");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (getTag().equals("getProvinceToActivity"))
                {

//                    ((SubmitActivity) getActivity()).onUserSelectProvince(provincesList.get(i));
//                    dismiss();
                    //call method from another fragment
                    ((ProfileFragment) getActivity()
                            .getSupportFragmentManager()
                            .findFragmentByTag("profileFrag")
                    ).onUserSelectProvince(provincesList.get(i));
                    dismiss();

                }

            }
        });

        return view;
    }

    private String loadJSONFromAsset(String filename) {
        String json = null;
        try {

            InputStream is = getActivity().getAssets().open(filename);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

}
