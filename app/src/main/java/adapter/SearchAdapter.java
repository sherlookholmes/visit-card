package adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sherlookhohlmes.android.Appconfig;
import com.example.sherlookhohlmes.android.IntroActivity;
import com.example.sherlookhohlmes.android.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fragment.SearchDetailFragment;
import model.Advertising;

/**
 * Created by SherlookHohlmes on 4/14/2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<Advertising> advertisings;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout layout;

        public ViewHolder(ConstraintLayout v) {
            super(v);
            layout = v;
        }

        public TextView txtTitle, txtPrice, txtCity;
        public ImageView image;
        public LinearLayout linMain;

    }

    public SearchAdapter(ArrayList<model.Advertising> advertisings, Context context) {
        this.advertisings = advertisings;
        this.context = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_inflate, parent, false);

        MyAdapter.ViewHolder vh = new MyAdapter.ViewHolder(v);

        vh.txtTitle = (TextView) v.findViewById(R.id.txt_title);
        vh.image = (ImageView) v.findViewById(R.id.image);
        vh.txtPrice = (TextView) v.findViewById(R.id.txt_province);
        vh.linMain = (LinearLayout) v.findViewById(R.id.lin_main);
        vh.txtCity = (TextView) v.findViewById(R.id.textView7);

        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position) {
        Log.e("condition--->", "OK");
        holder.txtTitle.setText(advertisings.get(position).getTitle());
        holder.txtCity.setText(advertisings.get(position).getCity());
        Log.e("url--->", advertisings.get(position).getImage_url());
        Glide.with(context).load(advertisings.get(position).getImage_url()).into(holder.image);

        holder.linMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Advertising selectedAdvertising = advertisings.get(position);
                JSONObject object = new JSONObject();
                try {
                    object.put("id", selectedAdvertising.getId());
                    object.put("title", selectedAdvertising.getTitle());
                    object.put("cat_id", selectedAdvertising.getCatid());
                    object.put("location", selectedAdvertising.getLocation());
                    object.put("description", selectedAdvertising.getDescription());
                    object.put("image", selectedAdvertising.getImage_url());
                    object.put("email", selectedAdvertising.getEmail());
                    object.put("phone", selectedAdvertising.getPhone());
                    object.put("city", selectedAdvertising.getCity());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("selectedadvid-->", selectedAdvertising.getId() + "");
                Bundle bundle = new Bundle();
                bundle.putString("advertising", object + "");
                //set Fragmentclass Arguments
                SearchDetailFragment fragobj = new SearchDetailFragment();
                fragobj.setArguments(bundle);

                android.support.v4.app.Fragment detailFragment = Appconfig.fm.findFragmentByTag("detailFragment");
                if (detailFragment == null) {
                    //not exist
                    Appconfig.fm.beginTransaction().add(R.id.frm_main_container, fragobj, "detailFragment").commit();
                    IntroActivity.hideShowFragment(fragobj);

                }
                else{
                    //fragment exist
                    Appconfig.fm.beginTransaction().remove(detailFragment).commit();
                    Appconfig.fm.beginTransaction().add( R.id.frm_main_container, fragobj, "detailFragment").commit();
                    IntroActivity.hideShowFragment(fragobj);

                }



            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.e("advertisingssize-->", advertisings.size() + "");
        return advertisings.size();
    }



}
