package adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fragment.CategoryListFragment;
import fragment.HomeFragment;
import model.Advertising;

/**
 * Created by SherlookHohlmes on 4/3/2018.
 */

public class CustomAdapter extends BaseAdapter {

    private ArrayList<Advertising> advertisings;
    private RequestQueue MyRequestQueue;
    String [] result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public CustomAdapter(Context context, String[] prgmNameList, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        this.context = context;
        imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.category_list, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);

        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[position]);

        rowView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                getWithCatId(position);

            }
        });

        return rowView;
    }

    private void getWithCatId(int possition) {

        Bundle bundle = new Bundle();
        bundle.putString("possition", possition + "");
        //set Fragmentclass Arguments
        CategoryListFragment fragment = new CategoryListFragment();
        Appconfig.catListFragment = fragment;
        fragment.setArguments(bundle);
        Appconfig.fm.beginTransaction().add(R.id.frm_detail_container, fragment).commit();
        IntroActivity.hideShowFragment(fragment);

    }

}
