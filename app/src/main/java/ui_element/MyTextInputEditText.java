package ui_element;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;

import com.example.sherlookhohlmes.android.R;

/**
 * Created by SherlookHohlmes on 4/14/2018.
 */

public class MyTextInputEditText extends TextInputEditText {
    public MyTextInputEditText(Context context) {
        super(context);
        setType(context);
    }

    public MyTextInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setType(context);
    }

    public MyTextInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
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
