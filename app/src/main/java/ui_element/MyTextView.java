package ui_element;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.widget.TextViewCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.sherlookhohlmes.android.R;

/**
 * Created by SherlookHohlmes on 4/14/2018.
 */

public class MyTextView extends android.support.v7.widget.AppCompatTextView {

    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setType(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setType(context);
    }

    public MyTextView(Context context) {
        super(context);
        setType(context);
    }

    private void setType(Context context)
    {
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "fonts/iransans.ttf"));

        this.setShadowLayer(0f, 5, 5, getContext().getResources().getColor(R.color.gray_lighter));
    }

}
