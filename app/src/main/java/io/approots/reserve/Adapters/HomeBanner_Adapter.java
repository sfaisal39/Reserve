package io.approots.reserve.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import io.approots.reserve.Activities.Home_Details;
import io.approots.reserve.Models.Home_Banner;
import io.approots.reserve.R;
import io.approots.reserve.Utilites.RoundedCornersTransform;

public class HomeBanner_Adapter extends PagerAdapter {


    private List<Home_Banner> IMAGES;
    private LayoutInflater inflater;
    private Context context;


    public HomeBanner_Adapter(Context context, List<Home_Banner> IMAGES) {
        this.context = context;
        this.IMAGES = IMAGES;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);

        //                .transform(new MaskTransformation(context, R.drawable.banner))
        Picasso.with(context)
                .load(IMAGES.get(position).getTB_IMG())
//                .transform(new RoundedCornersTransform())
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent asd = new Intent(context, Home_Details.class);
                asd.putExtra("TB_ID", IMAGES.get(position).getTB_ID());
                asd.putExtra("Tb_Type", IMAGES.get(position).getTB_TYPE());
                asd.putExtra("TB_NAME", IMAGES.get(position).getTB_NAME());
                context.startActivity(asd);

            }
        });


        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}




