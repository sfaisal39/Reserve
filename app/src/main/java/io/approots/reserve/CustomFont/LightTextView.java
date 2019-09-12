package io.approots.reserve.CustomFont;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class LightTextView extends android.support.v7.widget.AppCompatTextView {

    public LightTextView(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "Cairo-Light.ttf");
        this.setTypeface(face);
    }

    public LightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "Cairo-Light.ttf");
        this.setTypeface(face);
    }

    public LightTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "Cairo-Light.ttf");
        this.setTypeface(face);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }
}
