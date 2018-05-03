package fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.sherlookhohlmes.android.Appconfig;
import com.example.sherlookhohlmes.android.IntroActivity;
import com.example.sherlookhohlmes.android.R;

import adapter.CustomAdapter;

/**
 * Created by SherlookHohlmes on 4/2/2018.
 */

public class CatFragment extends Fragment {

    private View view;
    private GridView gv;
    public static String [] catNameList={"املاک",
            "وسایل نقلیه" ,
            "لوازم الکترونیکی",
            "مربوط به خانه",
            "خدمات",
            "وسایل شخصی",
            "سرگرمی و فراغت",
            "اجتماعی",
            "برای کسب و کار",
    "استخدام و کاریابی"};
    public static int [] catImages = {R.drawable.ic_building,
            R.drawable.ic_car,
            R.drawable.ic_electronices,
            R.drawable.ic_homeapliences,
            R.drawable.ic_support,
            R.drawable.ic_wristwatch,
            R.drawable.ic_leisure,
            R.drawable.ic_communication,
            R.drawable.ic_working,
    R.drawable.ic_businessman};

    public static CatFragment newInstance() {
        CatFragment fragment = new CatFragment();
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
        view = inflater.inflate(R.layout.fragment_cat, container, false);

        gv = (GridView) view.findViewById(R.id.gridView1);
        gv.setAdapter(new CustomAdapter(getActivity(), catNameList , catImages));

        return view;
    }

}
