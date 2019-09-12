package io.approots.reserve.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

import io.approots.reserve.CustomFont.RegularTextView;
import io.approots.reserve.CustomFont.SemiBoldTextView;
import io.approots.reserve.Models.CalDay_List;
import io.approots.reserve.R;

public class CalenderDayAdapter extends RecyclerView.Adapter<CalenderDayAdapter.Viewholder> {
    private Context context;
    private List<CalDay_List> myNoti;
    int POS = -1;
    private CalenderDayAdapter.OnDayClickListener clickListener;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    public interface OnDayClickListener {
        void onDayClick(int position);
    }

    public CalenderDayAdapter(Context context, List<CalDay_List> myNoti) {
        this.context = context;
        this.myNoti = myNoti;
    }

    public void setOnDayClickListener(CalenderDayAdapter.OnDayClickListener itemClickListener) {
        clickListener = itemClickListener;
    }

    @Override
    public CalenderDayAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_view, parent, false);
        return new CalenderDayAdapter.Viewholder(itemview, clickListener);
    }

    @Override
    public void onBindViewHolder(final CalenderDayAdapter.Viewholder holder, final int position) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


//        Log.i("ZAAT",myNoti.get(position).getDATE());
        holder.date.setText(myNoti.get(position).getDATE());
        holder.day.setText(myNoti.get(position).getDAY());
//        setWhite(holder.rela, holder.date, holder.day);

        if (!myNoti.get(position).getSLOT_RESERVE().equals("0")) {
            setRed(holder.rela, holder.date, holder.day);
        } else {
            setWhite(holder.rela, holder.date, holder.day);
//            if (POS == -1)
//                POS = 0;
        }


        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (POS == position) {

            Log.i("LOF", String.valueOf(POS));
            setGrey(holder.rela, holder.date, holder.day);

        } else {
            if (!myNoti.get(position).getSLOT_RESERVE().equals("0")) {
                setRed(holder.rela, holder.date, holder.day);
            } else {
                setWhite(holder.rela, holder.date, holder.day);
//            if (POS == -1)
//                POS = 0;
            }
//            setWhite(holder.rela, holder.date, holder.day);
//            if (myNoti.get(position).getSLOT_RESERVE().equals("1")) {
//
//                setRed(holder.rela, holder.date, holder.day);
//            }
        }


    }

    private void setGrey(RelativeLayout rela, SemiBoldTextView date, RegularTextView day) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        date.setTextColor(context.getResources().getColor(R.color.ColorBlackage));
        day.setTextColor(context.getResources().getColor(R.color.colorGrey_text));

        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            rela.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.grey));

        } else {
            rela.setBackground(context.getResources().getDrawable(R.drawable.grey));
//
        }

    }

    private void setRed(RelativeLayout rela, SemiBoldTextView date, RegularTextView day) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        date.setTextColor(context.getResources().getColor(R.color.colorWhite));
        day.setTextColor(context.getResources().getColor(R.color.colorWhite));

        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            rela.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.red));

        } else {
            rela.setBackground(context.getResources().getDrawable(R.drawable.red));
//
        }
    }

    private void setWhite(RelativeLayout rela, SemiBoldTextView date, RegularTextView day) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        date.setTextColor(context.getResources().getColor(R.color.ColorBlackage));
        day.setTextColor(context.getResources().getColor(R.color.colorGrey_text));

        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            rela.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.other));

        } else {
            rela.setBackground(context.getResources().getDrawable(R.drawable.other));
//
        }
    }

    public int getItemCount() {
        return myNoti.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {


        public SemiBoldTextView date;
        public RegularTextView day;
        public RelativeLayout rela;
        //        public SemiBoldTextView area_name;
//        public ImageView time_img;
//        public LinearLayout linesr;


//        public CustomTextView shipping_bnt;

        public Viewholder(View itemView, final CalenderDayAdapter.OnDayClickListener listener) {
            super(itemView);

            date = itemView.findViewById(R.id.datea);
            day = itemView.findViewById(R.id.daya);
            rela = itemView.findViewById(R.id.rela);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDayClick(position);
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

