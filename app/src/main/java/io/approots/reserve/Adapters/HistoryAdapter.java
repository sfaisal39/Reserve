package io.approots.reserve.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.approots.reserve.Activities.Clinic_details;
import io.approots.reserve.Activities.History.Order_datailActivity;
import io.approots.reserve.Activities.Shop;
import io.approots.reserve.CustomFont.RegularTextView;
import io.approots.reserve.CustomFont.SemiBoldTextView;
import io.approots.reserve.Models.History_List;
import io.approots.reserve.R;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.Viewholder> {
    private Context context;
    private List<History_List> myNoti;


    public HistoryAdapter(Context context, List<History_List> myNoti) {

        this.context = context;
        this.myNoti = myNoti;
    }


    @Override
    public HistoryAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_view, parent, false);
        return new HistoryAdapter.Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(final HistoryAdapter.Viewholder holder, final int position) {

        final int sdk = android.os.Build.VERSION.SDK_INT;
        holder.name.setText(myNoti.get(position).getTB_NAME());
        holder.dsc_text.setText(myNoti.get(position).getTB_DESC());
        holder.date.setText(myNoti.get(position).getTB_TIME());
        holder.status_label.setText(myNoti.get(position).getTB_STATUS_LABEL());
        holder.date_date.setText(myNoti.get(position).getTB_RESERVE_DATE());
        if (myNoti.get(position).getTB_STATUS().equals("1")) {

            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.circle_status.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.round_gry));
            } else {
                holder.circle_status.setBackground(context.getResources().getDrawable(R.drawable.round_gry));
            }
        } else if (myNoti.get(position).getTB_STATUS().equals("2")) {
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.circle_status.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.round_green));
            } else {
                holder.circle_status.setBackground(context.getResources().getDrawable(R.drawable.round_green));
            }

        } else if (myNoti.get(position).getTB_STATUS().equals("3")) {
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.circle_status.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.round_red));
            } else {
                holder.circle_status.setBackground(context.getResources().getDrawable(R.drawable.round_red));
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Order_datailActivity.class).putExtra("Tb_Type", myNoti.get(position).getTB_TYPE()).putExtra("CUST_NAME", myNoti.get(position).getTB_CUST_NAME()).putExtra("NAME", myNoti.get(position).getTB_NAME()).putExtra("STATUS_LABEL", myNoti.get(position).getTB_STATUS_LABEL()).putExtra("PLATE_NO", myNoti.get(position).getTB_PLATE_NO()).putExtra("PHONE", myNoti.get(position).getTB_PHONE()).putExtra("FULL_DATE", myNoti.get(position).getTB_RESERVE_DATE()).putExtra("ORDER_ID", myNoti.get(position).getTB_ID()).putExtra("Time", myNoti.get(position).getTB_TIME()));
            }
        });

    }


    public int getItemCount() {
        return myNoti.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {


        RegularTextView dsc_text, name, date, date_date, status_label;
        RelativeLayout circle_status;


//        public CustomTextView shipping_bnt;

        public Viewholder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            status_label = itemView.findViewById(R.id.status_label);
            date = itemView.findViewById(R.id.date);
            date_date = itemView.findViewById(R.id.date_date);
            dsc_text = itemView.findViewById(R.id.dsc);
            circle_status = itemView.findViewById(R.id.circle_status);


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

