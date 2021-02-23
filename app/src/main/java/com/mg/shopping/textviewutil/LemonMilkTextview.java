package com.mg.shopping.textviewutil;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.mg.shopping.fontutil.Font;

/**
 * Created by hp on 5/20/2018.
 */

public class LemonMilkTextview extends AppCompatTextView {
    public LemonMilkTextview(Context context) {
        super(context);
        setFont(context);
    }

    public LemonMilkTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public LemonMilkTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }

    private void setFont(Context context) {
        setTypeface(Font.lemonMilkFont(context));
    }
}

