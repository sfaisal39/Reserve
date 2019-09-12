package io.approots.reserve.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.approots.reserve.CustomFont.RegularTextView;
import io.approots.reserve.Models.TimeSlop_List;
import io.approots.reserve.R;

public class TimeSlopAdapter extends RecyclerView.Adapter<TimeSlopAdapter.Viewholder> {
    private Context context;
    private List<TimeSlop_List> myNoti;
    int POS = -1;
    private TimeSlopAdapter.OnSlotClickListener clickListener;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    Dialog dialog;

    public interface OnSlotClickListener {
        void onSlotClick(int position);
    }

    public TimeSlopAdapter(Context context, List<TimeSlop_List> myNoti, Dialog dialog) {
        this.context = context;
        this.myNoti = myNoti;
        this.dialog = dialog;

    }

    public void setOnAreaClickListener(TimeSlopAdapter.OnSlotClickListener itemClickListener) {
        clickListener = itemClickListener;
    }

    @Override
    public TimeSlopAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.slot_view, parent, false);
        POS = -1;
        return new TimeSlopAdapter.Viewholder(itemview, clickListener);
    }

    @Override
    public void onBindViewHolder(final TimeSlopAdapter.Viewholder holder, final int position) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        holder.timetext.setText(myNoti.get(position).getTB_TIME());
        holder.SLOT_AVAIABLE_LABEL.setText(myNoti.get(position).getSLOT_AVAIABLE_LABEL());

//        setWhite(holder.linesr, holder.time_img, holder.timetext);
//        Log.i("PARA", Integer.valueOf(myNoti.get(position).getORDER_REMAIN()));
        final int sdk = android.os.Build.VERSION.SDK_INT;

        if (Integer.valueOf(myNoti.get(position).getORDER_REMAIN()) < 0) {
            holder.timetext.setPaintFlags(holder.timetext.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        if (myNoti.get(position).getSLOT_AVAIABLE().equals("No")) {

            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.circle_status.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.round_red));
            } else {
                holder.circle_status.setBackground(context.getResources().getDrawable(R.drawable.round_red));
            }
        } else {
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.circle_status.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.round_green));
            } else {
                holder.circle_status.setBackground(context.getResources().getDrawable(R.drawable.round_green));
            }

        }


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {


        if (POS == position) {

            if (!myNoti.get(position).getSLOT_AVAIABLE().equals("No")) {
                SharedPreferences.Editor edsit = sharedpreferences.edit();
                edsit.putString("SLOTID", myNoti.get(position).getSLOT_ID() + myNoti.get(position).getTB_ID());
                edsit.putString("TBID", myNoti.get(position).getTB_ID());
                edsit.putString("DTIME", myNoti.get(position).getTB_TIME() + "(" + myNoti.get(position).getFULL_DATE() + ")");
                edsit.putString("DELIVERY_SLOTID", myNoti.get(position).getSLOT_ID());
                edsit.putString("DELIVERY_DATE", myNoti.get(position).getFULL_DATE());
                edsit.putString("DELIVERY_TIME", myNoti.get(position).getTB_TIME());
                edsit.commit();
                setRed(holder.linesr, holder.SLOT_AVAIABLE_LABEL, holder.timetext);
            }

        } else {

            setWhite(holder.linesr, holder.SLOT_AVAIABLE_LABEL, holder.timetext);


        }


        if (POS == -1) {
            if (sharedpreferences.contains("SLOTID")) {
//                Log.i("PARA", sharedpreferences.getString("SLOTID", ""));
                if ((myNoti.get(position).getSLOT_ID() + myNoti.get(position).getTB_ID()).equals(sharedpreferences.getString("SLOTID", ""))) {
                    setRed(holder.linesr, holder.SLOT_AVAIABLE_LABEL, holder.timetext);
                } else {
                    setWhite(holder.linesr, holder.SLOT_AVAIABLE_LABEL, holder.timetext);
                }
            }
        }

//        Picasso.
//                with(context)
//                .load(myNoti.get(position).getTB_IMG())
//                .into(holder.img);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent ni = new Intent(context, CatDetail_Activity.class);
//                ni.putExtra("Title", myNoti.get(position).getTB_NAME());
//                ni.putExtra("Sub_Array", myNoti.get(position).getSub_Arrray());
//                ni.putExtra("Cat_Id",  myNoti.get(position).getCAT_ID());
//                context.startActivity(ni);
//
//            }
//        });
    }

    private void setWhite(RelativeLayout linesr, RegularTextView time_img, RegularTextView timetext) {
        final int sdk = android.os.Build.VERSION.SDK_INT;

//        Picasso.
//                with(context)
//                .load(R.drawable.timer)
//                .into(time_img);
        timetext.setTextColor(context.getResources().getColor(R.color.ColorBlackage));
        time_img.setTextColor(context.getResources().getColor(R.color.colorBlack));

        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            linesr.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.rounded_slot));
        } else {
            linesr.setBackground(context.getResources().getDrawable(R.drawable.rounded_slot));

        }
    }

    private void setRed(RelativeLayout linesr, RegularTextView time_img, RegularTextView timetext) {
        final int sdk = Build.VERSION.SDK_INT;
//        Picasso.
//                with(context)
//                .load(R.drawable.timerwhite)
//                .into(time_img);
        timetext.setTextColor(context.getResources().getColor(R.color.colorWhite));
        time_img.setTextColor(context.getResources().getColor(R.color.colorWhite));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            linesr.setBackground(context.getResources().getDrawable(R.drawable.rounded_red));
        } else {
            linesr.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.rounded_red));

        }
    }


    public int getItemCount() {
        return myNoti.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {


        public RegularTextView timetext, SLOT_AVAIABLE_LABEL;
        //        public SemiBoldTextView area_name;
        public ImageView time_img;
        public RelativeLayout linesr, circle_status;


//        public CustomTextView shipping_bnt;

        public Viewholder(View itemView, final TimeSlopAdapter.OnSlotClickListener listener) {
            super(itemView);

            timetext = itemView.findViewById(R.id.timetext);
            time_img = itemView.findViewById(R.id.time_img);
            linesr = itemView.findViewById(R.id.slot_rav);
            SLOT_AVAIABLE_LABEL = itemView.findViewById(R.id.SLOT_AVAIABLE_LABEL);
            circle_status = itemView.findViewById(R.id.circle_status);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onSlotClick(position);
                        }
                        POS = getPosition();
                    }
                }
            });

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



