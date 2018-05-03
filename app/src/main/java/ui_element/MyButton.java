package ui_element;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.example.sherlookhohlmes.android.R;

/**
 * Created by SherlookHohlmes on 4/19/2018.
 */

public class MyButton extends AppCompatButton {
    public MyButton(Context context) {
        super(context);
        setType(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setType(context);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setType(context);
    }

    private void setType(Context context)
    {
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "fonts/iransans.ttf"));

        this.setShadowLayer(0f, 5, 5, getContext().getResources().getColor(R.color.gray_lighter));
    }

}
