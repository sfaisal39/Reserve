package io.approots.reserve.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.approots.reserve.Activities.Home_Details;
import io.approots.reserve.Activities.WebActivity;
import io.approots.reserve.Models.AdOption_Model;
import io.approots.reserve.R;

public class Adoption_Adapter extends PagerAdapter {


    private List<AdOption_Model> IMAGES;
    private LayoutInflater inflater;
    private Context context;


    public Adoption_Adapter(Context context, List<AdOption_Model> IMAGES) {
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

        if (!IMAGES.get(position).getTB_WEB().equals("")) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent asd = new Intent(context, WebActivity.class);
                    asd.putExtra("URL", IMAGES.get(position).getTB_WEB());
//                asd.putExtra("Tb_Type", IMAGES.get(position).getTB_TYPE());
//                asd.putExtra("TB_NAME", IMAGES.get(position).getT);
                    context.startActivity(asd);

                }
            });
        }


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




