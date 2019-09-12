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

import io.approots.reserve.Activities.Clinic_details;
import io.approots.reserve.Activities.PROD_Activity;
import io.approots.reserve.Activities.Shop;
import io.approots.reserve.CustomFont.RegularTextView;
import io.approots.reserve.CustomFont.SemiBoldTextView;
import io.approots.reserve.Models.PROD_Model;
import io.approots.reserve.R;

public class PROD_Adapter extends RecyclerView.Adapter<PROD_Adapter.Viewholder> {
    private Context context;
    private List<PROD_Model> myNoti;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    String Name1, Name2, Type;


    public PROD_Adapter(Context context, List<PROD_Model> myNoti, String Name1, String Name2, String Type) {

        this.context = context;
        this.myNoti = myNoti;
        this.Name1 = Name1;
        this.Name2 = Name2;
        this.Type = Type;
//        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }


    @Override
    public PROD_Adapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.homedetail_view, parent, false);
        return new PROD_Adapter.Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(final PROD_Adapter.Viewholder holder, final int position) {

        holder.name.setText(myNoti.get(position).getTB_NAME());
        holder.dsc_text.setText(myNoti.get(position).getTB_DESC());


        Picasso.
                with(context)
                .load(myNoti.get(position).getTB_IMG())
                .into(holder.img);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Type.equals("1")) {
                    context.startActivity(new Intent(context, Clinic_details.class).putExtra("TB_ID", myNoti.get(position).getTB_ID()).putExtra("TB_NAME", Name1).putExtra("TB_NAME2", Name2).putExtra("TB_NAME3", myNoti.get(position).getTB_NAME()).putExtra("Tb_Type", Type).putExtra("TB_DESC", myNoti.get(position).getTB_DESC()).putExtra("TB_ADDRESS", myNoti.get(position).getTB_ADDRESS()).putExtra("TB_ABOUT", myNoti.get(position).getTB_ABOUT()).putExtra("TB_LATI", myNoti.get(position).getTB_LATI()).putExtra("TB_LONG", myNoti.get(position).getTB_LONG()).putExtra("TB_PHONE1", myNoti.get(position).getTB_PHONE1()).putExtra("TB_PHONE2", myNoti.get(position).getTB_PHONE2()).putExtra("TB_IMG", myNoti.get(position).getTB_IMG()).putExtra("TB_ID", myNoti.get(position).getTB_ID()).putExtra("DATA", myNoti.get(position).getBRANCHES_ARRAY()));
                } else if (Type.equals("2")) {
                    context.startActivity(new Intent(context, Shop.class).putExtra("TB_ID", myNoti.get(position).getTB_ID()).putExtra("TB_NAME", Name1).putExtra("TB_NAME2", Name2).putExtra("TB_NAME3", myNoti.get(position).getTB_NAME()).putExtra("Tb_Type", Type).putExtra("TB_DESC", myNoti.get(position).getTB_DESC()).putExtra("TB_ADDRESS", myNoti.get(position).getTB_ADDRESS()).putExtra("TB_ABOUT", myNoti.get(position).getTB_ABOUT()).putExtra("TB_LATI", myNoti.get(position).getTB_LATI()).putExtra("TB_LONG", myNoti.get(position).getTB_LONG()).putExtra("TB_PHONE1", myNoti.get(position).getTB_PHONE1()).putExtra("TB_PHONE2", myNoti.get(position).getTB_PHONE2()).putExtra("TB_IMG", myNoti.get(position).getTB_IMG()).putExtra("TB_ID", myNoti.get(position).getTB_ID()).putExtra("DATA", myNoti.get(position).getBRANCHES_ARRAY()));
                }

//                context.startActivity(new Intent(context, PROD_Activity.class).putExtra("TB_ID", myNoti.get(position).getTB_ID()).putExtra("TB_NAME", Name1).putExtra("TB_NAME2", myNoti.get(position).getTB_NAME()).putExtra("Tb_Type", Type));

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
