package com.mg.shopping.textviewutil;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.mg.shopping.fontutil.Font;

public class AquaticoRegularTextview extends AppCompatTextView {
    public AquaticoRegularTextview(Context context) {
        super(context);
        setFont(context);
    }

    public AquaticoRegularTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public AquaticoRegularTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }

    private void setFont(Context context) {
        setTypeface(Font.aquaticoRegularFont(context));
    }
}

