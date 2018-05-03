package fragment;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sherlookhohlmes.android.Appconfig;
import com.example.sherlookhohlmes.android.IntroActivity;
import com.example.sherlookhohlmes.android.R;

import org.json.JSONException;
import org.json.JSONObject;

import model.Advertising;
import model.Category;

/**
 * Created by SherlookHohlmes on 4/3/2018.
 */

public class DetailFragment extends Fragment {

    private View view;
    private Advertising advertising;
    private ImageView imageView;
    private TextView txtTitle, txtCat, txtProvince, txtCity, txtPrice, txtType, txtDescription, txtPhone, txtEmail;

    public static void setLightStatusBar(View view,Activity activity){


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

            view.setSystemUiVisibility(flags);
            activity.getWindow().setStatusBarColor(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_collapse, container, false);
        setLightStatusBar(view, getActivity());
        imageView = (ImageView) view.findViewById(R.id.imageview);
        txtTitle = (TextView) view.findViewById(R.id.txt_title);
        txtCat = (TextView) view.findViewById(R.id.txt_cat);
        txtProvince = (TextView) view.findViewById(R.id.txt_province);
        txtCity = (TextView) view.findViewById(R.id.txt_city);
        txtPhone = (TextView) view.findViewById(R.id.txt_phone);
        txtEmail = (TextView) view.findViewById(R.id.txt_email);
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

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder)
                .centerCrop()
                .error(R.drawable.ic_error_black_24dp);
        Glide.with(getActivity())
                .setDefaultRequestOptions(requestOptions)
                .load(advertising.getImage_url()).into(imageView);

        txtTitle.setText(advertising.getTitle());
        txtCat.setText(Category.getCatWithId(advertising.getCatid()));
        txtProvince.setText(advertising.getLocation());
        txtCity.setText(advertising.getCity());
        txtPhone.setText(advertising.getPhone());
        txtEmail.setText(advertising.getEmail());
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

                    Appconfig.fm.beginTransaction().remove(DetailFragment.this).commit();
                    IntroActivity.hideShowFragment(Appconfig.defaultFragment);

                    clearLightStatusBar(getActivity(), view);

                    return true;

                }

                return false;
            }
        });

    }

    public static void clearLightStatusBar(Activity activity,View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            Window window = activity.getWindow();
            window.setStatusBarColor(ContextCompat
                    .getColor(activity,R.color.colorPrimaryDark));

        }
    }

}
