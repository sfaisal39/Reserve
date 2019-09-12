package io.approots.reserve.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.approots.reserve.CustomFont.RegularTextView;
import io.approots.reserve.Models.Notification_Model;
import io.approots.reserve.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Viewholder> {
    private Context context;
    private List<Notification_Model> myNoti;


    public NotificationAdapter(Context context, List<Notification_Model> myNoti) {

        this.context = context;
        this.myNoti = myNoti;
    }


    @Override
    public NotificationAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_view, parent, false);
        return new NotificationAdapter.Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(final NotificationAdapter.Viewholder holder, final int position) {

        final int sdk = android.os.Build.VERSION.SDK_INT;
        holder.name.setText(myNoti.get(position).getTB_NAME());
        holder.dsc_text.setText(myNoti.get(position).getTB_DESC());
        holder.date.setText(myNoti.get(position).getTB_DATE());
//        Picasso.with(context).load(myNoti.get(position).getTB_IMG()).placeholder(R.drawable.logo_trans).into(holder.date);

    }


    public int getItemCount() {
        return myNoti.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {


        RegularTextView dsc_text, name,date;
//        ImageView date;


//        public CustomTextView shipping_bnt;

        public Viewholder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.Date);
            dsc_text = itemView.findViewById(R.id.dsc);


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


