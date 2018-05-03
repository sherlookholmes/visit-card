package ui_element;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import com.example.sherlookhohlmes.android.R;

/**
 * Created by SherlookHohlmes on 4/14/2018.
 */

public class MyBottomBar extends com.roughike.bottombar.BottomBar {


    public MyBottomBar(Context context) {
        super(context);
        setType(context);
    }

    public MyBottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setType(context);
    }

    public MyBottomBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setType(context);
    }

    public MyBottomBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setType(context);
    }

    private void setType(Context context)
    {
        this.setTabTitleTypeface(Typeface.createFromAsset(context.getAssets(),
                "fonts/iransans.ttf"));
    }

}
