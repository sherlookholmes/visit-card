package fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sherlookhohlmes.android.Appconfig;
import com.example.sherlookhohlmes.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import model.Category;

/**
 * Created by SherlookHohlmes on 4/4/2018.
 */

public class ChooseCategoryFrgment extends DialogFragment {

    private View view;
    private ListView listView;
    private ArrayList<Category> categoriesList;
    private JSONArray categories;
    private ArrayList<String> finalCategories = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_choosecategory_frgment, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        listView = (ListView) view.findViewById(R.id.category_listView);

        categoriesList = new ArrayList<>();
        try {
            categories = new JSONArray(loadJSONFromAsset("category.json"));
            for (int i = 0; i <= categories.length(); i ++)
            {

                JSONObject object = categories.getJSONObject(i);
                Category category = new Category(object.getString("id"), object.getString("name"));
                categoriesList.add(category);
                finalCategories.add(category.getName());

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item, finalCategories);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (getTag().equals("getCategoryToFragment"))
                {

                    //call method from another fragment
                    ((ProfileFragment) getActivity()
                            .getSupportFragmentManager()
                            .findFragmentByTag("profileFrag")
                    ).onUserSelectCategory(categoriesList.get(i));
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
