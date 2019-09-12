package io.approots.reserve.CustomFont;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class SemiBoldTextView extends android.support.v7.widget.AppCompatTextView {

    public SemiBoldTextView(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "cairosemibold.ttf");
        this.setTypeface(face);
    }

    public SemiBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "cairosemibold.ttf");
        this.setTypeface(face);
    }

    public SemiBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "cairosemibold.ttf");
        this.setTypeface(face);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }
}

