package io.approots.reserve.Activities.History;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import io.approots.reserve.Adapters.CalSlopAdapter;
import io.approots.reserve.Adapters.HistoryAdapter;
import io.approots.reserve.CustomFont.RegularTextView;
import io.approots.reserve.Models.CalDay_List;
import io.approots.reserve.Models.History_List;
import io.approots.reserve.R;
import io.approots.reserve.Utilites.Constants;
import io.approots.reserve.Utilites.MySingleton;

public class FragmentReservation extends Fragment {

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    private HistoryAdapter adapter;
    private List<History_List> Card_data;
    private RecyclerView slot_Recycler;
    private ShimmerFrameLayout mShimmerViewContainer;

    RegularTextView status_txt;
    View view;
    int status = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reservation, container, false);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        init();
        return view;

    }

    private void init() {
        Card_data = new ArrayList<>();
        slot_Recycler = view.findViewById(R.id.slot_Recycler);

        status_txt = (RegularTextView) view.findViewById(R.id.status);
        slot_Recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        slot_Recycler.addItemDecoration(new GridSpacingItemDecoration(5, dpToPx(10), false));
        slot_Recycler.setItemAnimator(new DefaultItemAnimator());
        adapter = new HistoryAdapter(getActivity(), Card_data);
        slot_Recycler.setAdapter(adapter);

//        if (status_txt.getText().equals(getString(R.string.history))) {
//            status = 0;
//        } else {
//            status = 0;
//        }
//        status_txt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (status_txt.getText().equals(getString(R.string.history))) {
//                    status = 1;
//                    status_txt.setText(getString(R.string.current));
//                    getResponse();
//                } else {
//                    status = 0;
//                    status_txt.setText(getString(R.string.history));
//                    getResponse();
//                }
//            }
//        });

    }

    @Override
    public void onStart() {
        super.onStart();
        getResponse();
    }

    @Override
    public void onResume() {
        super.onResume();
//        getResponse();
    }


    private void getResponse() {

        slot_Recycler.setAdapter(null);
        slot_Recycler.removeAllViewsInLayout();
        slot_Recycler.swapAdapter(adapter, true);
        Card_data.clear();

        adapter.notifyDataSetChanged();

        slot_Recycler.setAdapter(null);
        slot_Recycler.removeAllViewsInLayout();
        slot_Recycler.swapAdapter(adapter, true);
        Card_data.clear();

        adapter.notifyDataSetChanged();
        String lang = sharedpreferences.getString("LOCALES", "en");
        String USER_ID = sharedpreferences.getString("USER_ID", "");

        final String URL = Constants.BaseURL + "GET_RESERVE_HISTORY?sop=" + Constants.SOP + "&lang=" + lang + "&USERID=" + USER_ID + "&ALL_HISTORY=" + status;
        Log.i("CHDS", String.valueOf(URL));
        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.GET, URL, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("CHDS", String.valueOf(response));
                        try {
//                            progresss.setVisibility(View.GONE);
                            JSONObject ar = response.getJSONObject("result");
                            String sts = ar.getString("status");
                            String msg = ar.getString("msg");

                            if (sts.equals("1")) {

                                JSONArray arst = ar.getJSONArray("data");


                                for (int i = 0; i < arst.length(); i++) {
                                    JSONObject obj = arst.getJSONObject(i);
                                    History_List datas = new History_List(obj.getString("TB_NAME"), obj.getString("TB_DESC"), obj.getString("TB_TIME"), obj.getString("TB_STATUS"), obj.getString("TB_OTHERID"), obj.getString("TB_DELIVERY_SLOTID"), obj.getString("TB_DATE"), obj.getString("TB_RESERVE_DATE"), obj.getString("TB_ID"), obj.getString("TB_TIME_END"), obj.getString("TB_STATUS_LABEL"), obj.getString("TB_PLATE_NO"), obj.getString("TB_TYPE"), obj.getString("TB_PHONE"), obj.getString("TB_CUST_NAME"));
                                    Card_data.add(datas);
                                    adapter.notifyDataSetChanged();
                                }

                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putInt("ORDER", arst.length());
                                editor.apply();

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

}
