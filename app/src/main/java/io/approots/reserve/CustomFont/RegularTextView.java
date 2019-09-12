package io.approots.reserve.CustomFont;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Ramzan on 10/4/2017.
 */

public class RegularTextView extends android.support.v7.widget.AppCompatTextView {

    public RegularTextView(Context context) {
        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "cairoregular.ttf");
        this.setTypeface(face);
    }

    public RegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "cairoregular.ttf");
        this.setTypeface(face);
    }

    public RegularTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "cairoregular.ttf");
        this.setTypeface(face);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }
}
