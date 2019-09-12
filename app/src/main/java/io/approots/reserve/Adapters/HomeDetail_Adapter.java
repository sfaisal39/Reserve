package io.approots.reserve.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.approots.reserve.Activities.PROD_Activity;
import io.approots.reserve.CustomFont.RegularTextView;
import io.approots.reserve.CustomFont.SemiBoldTextView;
import io.approots.reserve.Models.HomeDetail_Model;
import io.approots.reserve.R;

public class HomeDetail_Adapter extends RecyclerView.Adapter<HomeDetail_Adapter.Viewholder> {
    private Context context;
    private List<HomeDetail_Model> myNoti;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    String Name1, Type;


    public HomeDetail_Adapter(Context context, List<HomeDetail_Model> myNoti, String Name1, String Type) {

        this.context = context;
        this.myNoti = myNoti;
        this.Name1 = Name1;
        this.Type = Type;
//        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }


    @Override
    public HomeDetail_Adapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.homedetail_view, parent, false);
        return new HomeDetail_Adapter.Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(final HomeDetail_Adapter.Viewholder holder, final int position) {

        holder.name.setText(myNoti.get(position).getTB_NAME());
        holder.dsc_text.setText(myNoti.get(position).getTB_DESC());


        Picasso.
                with(context)
                .load(myNoti.get(position).getTB_IMG())
                .into(holder.img);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(new Intent(context, PROD_Activity.class).putExtra("TB_ID", myNoti.get(position).getTB_ID()).putExtra("TB_NAME", Name1).putExtra("TB_NAME2", myNoti.get(position).getTB_NAME()).putExtra("Tb_Type", Type));

            }
        });

    }


    public int getItemCount() {
        return myNoti.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {


        public SemiBoldTextView name;
        RegularTextView dsc_text;
        ImageView img;


//        public CustomTextView shipping_bnt;

        public Viewholder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            img = itemView.findViewById(R.id.image);
            dsc_text = itemView.findViewById(R.id.dsc_text);


        }
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}

