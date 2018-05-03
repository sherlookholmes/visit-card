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

import model.City;
import model.Province;

/**
 * Created by SherlookHohlmes on 4/11/2018.
 */

public class CityDialog extends DialogFragment {

    private View view;
    private ArrayList<City> cities;
    private ArrayList<String> citiesList;
    private ListView listView;
    private Province province;

    public CityDialog(Province province) {
        this.province = province;
    }

    public CityDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_choosecity_frgment, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        listView = (ListView) view.findViewById(R.id.category_listView);

        try {

            JSONArray citiesJson = new JSONArray(loadJSONFromAsset("city.json"));
            cities = new ArrayList<>();
            citiesList = new ArrayList<>();

            for (int i = 0; i <= citiesJson.length(); i++)
            {

                JSONObject object = citiesJson.getJSONObject(i);
                if (object.getString("province").equals(province.getId())) {

                    City city = new City(object.getString("id"), object.getString("name"), object.getString("province"));
                    cities.add(city);
                    String cityName = city.getName();
                    citiesList.add(cityName);

                    Log.e("cities>>>", city + "");

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item, citiesList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (getTag().equals("getCityToFragment"))
                {

                    ((ProfileFragment) getActivity()
                            .getSupportFragmentManager()
                            .findFragmentByTag("profileFrag")
                    ).onUserSelectCity(cities.get(i));
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
