package com.mg.shopping.textviewutil;

import android.content.Context;
import android.util.AttributeSet;

import com.mg.shopping.fontutil.Font;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by hp on 5/20/2018.
 */

public class MenuTextview extends AppCompatTextView {
    public MenuTextview(Context context) {
        super(context);
        setFont(context);
    }

    public MenuTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public MenuTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }

    private void setFont(Context context) {
        setTypeface(Font.ubuntuRegularFont(context));
    }
}

