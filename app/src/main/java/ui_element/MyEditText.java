package ui_element;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

import com.example.sherlookhohlmes.android.R;

/**
 * Created by SherlookHohlmes on 4/15/2018.
 */

public class MyEditText extends android.support.v7.widget.AppCompatEditText {


    public MyEditText(Context context) {
        super(context);
        setType(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setType(context);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setType(context);
    }

    private void setType(Context context)
    {
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "fonts/iransans.ttf"));

        this.setShadowLayer(0f, 5, 5, getContext().getResources().getColor(R.color.gray_lighter));
    }

    @Override
    public boolean onKeyPreIme( int key_code, KeyEvent event )
    {
        if ( event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP )
            this.clearFocus();

        return super.onKeyPreIme( key_code, event );
    }

}
