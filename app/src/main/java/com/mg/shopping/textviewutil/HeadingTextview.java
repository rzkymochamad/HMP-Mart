package com.mg.shopping.textviewutil;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.mg.shopping.fontutil.Font;

/**
 * Created by hp on 5/20/2018.
 */

public class HeadingTextview extends AppCompatTextView {
    public HeadingTextview(Context context) {
        super(context);
        setFont(context);
    }

    public HeadingTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public HeadingTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }

    private void setFont(Context context) {
        setTypeface(Font.ubuntuBoldFont(context));
    }
}

