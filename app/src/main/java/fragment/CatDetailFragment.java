package fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sherlookhohlmes.android.Appconfig;
import com.example.sherlookhohlmes.android.IntroActivity;
import com.example.sherlookhohlmes.android.R;

import org.json.JSONException;
import org.json.JSONObject;

import model.Advertising;
import model.Category;

/**
 * Created by SherlookHohlmes on 4/12/2018.
 */

public class CatDetailFragment extends Fragment {

    private View view;
    private Advertising advertising;
    private ImageView imageView;
    private TextView txtTitle, txtCat, txtLocation, txtPrice, txtType, txtDescription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_collapse, container, false);
        imageView = (ImageView) view.findViewById(R.id.imageview);
        txtTitle = (TextView) view.findViewById(R.id.txt_title);
        txtCat = (TextView) view.findViewById(R.id.txt_cat);
        txtLocation = (TextView) view.findViewById(R.id.txt_province);
        txtPrice = (TextView) view.findViewById(R.id.txt_province);
        txtType = (TextView) view.findViewById(R.id.txt_type);
        txtDescription = (TextView) view.findViewById(R.id.txt_description);

        String str = getArguments().getString("advertising");
        try {
            JSONObject object = new JSONObject(str);
            advertising = new Advertising(object.getInt("id"),
                    object.getInt("cat_id"),
                    object.getString("title"),
                    object.getString("location"),
                    object.getString("description"),
                    object.getString("image"),
                    object.getString("email"),
                    object.getString("phone"),
                    object.getString("city"));

            Log.e("advid-->", advertising.getId() + "");

        } catch (JSONException e) {
            Log.e("error: ", "parsingerror " + e);
        }

        Glide.with(getActivity())
                .load(advertising.getImage_url())
                //.placeholder(R.drawable.loading_larj)
                .into(imageView);

        txtTitle.setText(advertising.getTitle());
        txtCat.setText(Category.getCatWithId(advertising.getCatid()));
        txtLocation.setText(advertising.getLocation());
        txtDescription.setText(advertising.getDescription());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    Appconfig.fm.beginTransaction().remove(CatDetailFragment.this).commit();
                    IntroActivity.hideShowFragment(Appconfig.catListFragment);

                    return true;

                }

                return false;
            }
        });

    }

}
