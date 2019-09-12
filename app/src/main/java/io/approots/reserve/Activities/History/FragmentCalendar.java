package io.approots.reserve.Activities.History;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.approots.reserve.Activities.Home_Details;
import io.approots.reserve.Activities.TimeSlot;
import io.approots.reserve.Adapters.CalSlopAdapter;
import io.approots.reserve.Adapters.CalenderDayAdapter;
import io.approots.reserve.Adapters.DayAdapter;
import io.approots.reserve.Adapters.HistoryAdapter;
import io.approots.reserve.Adapters.HomeDetail_Adapter;
import io.approots.reserve.Adapters.TimeSlopAdapter;
import io.approots.reserve.Models.CalDay_List;
import io.approots.reserve.Models.CalSlop_List;
import io.approots.reserve.Models.Day_List;
import io.approots.reserve.Models.History_List;
import io.approots.reserve.Models.HomeDetail_Model;
import io.approots.reserve.Models.TimeSlop_List;
import io.approots.reserve.R;
import io.approots.reserve.Utilites.Constants;
import io.approots.reserve.Utilites.MySingleton;


public class FragmentCalendar extends Fragment {

    View view;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";


    private CalenderDayAdapter dayadapter;
    private List<CalDay_List> Day_data;
    private RecyclerView Day_Recycler;

    private CalSlopAdapter adapter;
    private List<CalSlop_List> Card_data;
    private RecyclerView slot_Recycler;

    JSONArray Response;
    private ShimmerFrameLayout mShimmerViewContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendar, container, false);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        init();
        return view;

    }

    private void init() {
        Day_data = new ArrayList<>();
        Card_data = new ArrayList<>();
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Day_Recycler = (RecyclerView) view.findViewById(R.id.day_Recycler);
        slot_Recycler = (RecyclerView) view.findViewById(R.id.slot_Recycler);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5);
        Day_Recycler.setLayoutManager(gridLayoutManager);
//        Day_Recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        Day_Recycler.addItemDecoration(new GridSpacingItemDecoration(5, dpToPx(5), false));
        Day_Recycler.setItemAnimator(new DefaultItemAnimator());
        dayadapter = new CalenderDayAdapter(getActivity(), Day_data);
        Day_Recycler.setAdapter(dayadapter);

        slot_Recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        slot_Recycler.addItemDecoration(new GridSpacingItemDecoration(5, dpToPx(10), false));
        slot_Recycler.setItemAnimator(new DefaultItemAnimator());
        adapter = new CalSlopAdapter(getActivity(), Card_data);
        slot_Recycler.setAdapter(adapter);

        getResponse();

        dayadapter.setOnDayClickListener(new CalenderDayAdapter.OnDayClickListener() {
            @Override
            public void onDayClick(int position) {
                slot_Recycler.setAdapter(null);
                slot_Recycler.removeAllViewsInLayout();
                slot_Recycler.swapAdapter(adapter, false);
                Card_data.clear();

                adapter.notifyDataSetChanged();
                dayadapter.notifyDataSetChanged();
                Log.i("POJ", String.valueOf(position));
//                Toast.makeText(TimeSlot.this,position,Toast.LENGTH_SHORT).show();
                getSlot(position);


            }
        });
    }

    private void getSlot(final int pos) {

//        adapter.notifyDataSetChanged();
//
//        titletxt.setText(name);
//        Response
        slot_Recycler.setAdapter(null);
        slot_Recycler.removeAllViewsInLayout();
        slot_Recycler.swapAdapter(adapter, false);
        Card_data.clear();
        adapter.notifyDataSetChanged();


        try {

            JSONObject obj = Response.getJSONObject(pos);
            Log.i("POJ", String.valueOf(obj));

            JSONArray data_child = obj.getJSONArray("data_child");


            for (int i = 0; i < data_child.length(); i++) {
                JSONObject child = data_child.getJSONObject(i);
                CalSlop_List datas = new CalSlop_List(child.getString("TB_TIME"), child.getString("FULL_DATE"), child.getString("TB_NAME"), child.getString("TB_TYPE"), child.getString("TB_DELIVERY_SLOTID"), child.getString("TB_STATUS"), child.getString("TB_STATUS_LABEL"), child.getString("TB_PLATE_NO"), child.getString("TB_PHONE"), child.getString("TB_CUST_NAME"), child.getString("ORDER_ID"));
                Card_data.add(datas);
                adapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void getResponse() {

        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmer();
//        progresss.setVisibility(View.GONE);

        String lang = sharedpreferences.getString("LOCALES", "en");
        String USER_ID = sharedpreferences.getString("USER_ID", "");

        final String URL = Constants.BaseURL + "GET_CALENDAR_HISTORY?sop=" + Constants.SOP + "&lang=" + lang + "&TB_USERID=" + USER_ID;
        Log.i("CHDS", String.valueOf(URL));
        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.GET, URL, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("CHDS", String.valueOf(response));
                        try {
//                            progresss.setVisibility(View.GONE);
                            JSONObject ar = response.getJSONObject("result");
//                            text_des.setText(ar.getString("shortNote") + ar.getString("slotExpiry_mnt"));
                            String sts = ar.getString("status");
                            String msg = ar.getString("msg");

                            if (sts.equals("1")) {

//                                date_current.setText(ar.getString("month_header"));
                                mShimmerViewContainer.stopShimmer();
                                mShimmerViewContainer.setVisibility(View.GONE);

                                JSONArray arst = ar.getJSONArray("data");
//                                JSONObject obj = arst.getJSONObject(pos);
//                                JSONArray data_child = arst.getJSONArray("data_child");

                                Response = arst;
                                Log.i("POJ", String.valueOf(arst));

//                                if (sharedpreferences.contains("TBID")) {
////                                    Log.i("ONJ", String.valueOf(sharedpreferences.getString("TBID", "")));
////                                    Toast.makeText(TimeSlot.this, String.valueOf(sharedpreferences.getString("TBID", "")), Toast.LENGTH_SHORT).show();
//                                    getSlot(Integer.parseInt(sharedpreferences.getString("TBID", "")) - 1);
//                                } else {
////                                    Toast.makeText(TimeSlot.this, String.valueOf(0), Toast.LENGTH_SHORT).show();
//                                    getSlot(0);
//
//                                }

                                for (int i = 0; i < arst.length(); i++) {
                                    JSONObject obj = arst.getJSONObject(i);
//                                    JSONArray data_child = obj.getJSONArray("data_child");
//                                  /  Log.i("ONJ", String.valueOf(String.valueOf(obj.getString("DAY"))));
//                                    Toast.makeText(TimeSlot.this, String.valueOf(obj), Toast.LENGTH_SHORT).show();
                                    CalDay_List datas = new CalDay_List(obj.getString("DATE"), obj.getString("DAY"), obj.getString("Month"), obj.getString("FULL_DATE"), obj.getString("SLOT_RESERVE"), obj.getString("TB_ID"));
                                    Day_data.add(datas);
                                    dayadapter.notifyDataSetChanged();
                                }

                            } else {

//
                                String mskg = ar.getString("msg");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                VolleyLog.e("Error: ", error.getMessage());

                NetworkResponse networkResponse = error.networkResponse;


                if (error instanceof NetworkError) {

                    // Toast.makeText(getActivity(), "NetworkError ", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    //Toast.makeText(getActivity(), "ServerError" + error.toString(), Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {

                    //Toast.makeText(getActivity(), "AUTH", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {

//                    Toast.makeText(getActivity(), "PARSE ERROR", Toast.LENGTH_LONG).show();
                } else if (error instanceof NoConnectionError) {

//                    Toast.makeText(getActivity(), "NO CONNECTION", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
//                    SetGallery(subcat_id, pro_id);
                    getResponse();
                }
            }
        });
        MySingleton.getInstance(getActivity()).addToRequestQueue(request_json);

    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }


    }


    private int dpToPx(int i) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, r.getDisplayMetrics()));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
