package io.approots.reserve.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

import io.approots.reserve.Activities.History.FragmentCalendar;
import io.approots.reserve.Adapters.HistoryAdapter;
import io.approots.reserve.Adapters.NotificationAdapter;
import io.approots.reserve.CustomFont.RegularTextView;
import io.approots.reserve.Models.History_List;
import io.approots.reserve.Models.Notification_Model;
import io.approots.reserve.R;
import io.approots.reserve.Utilites.Constants;
import io.approots.reserve.Utilites.MySingleton;
import me.leolin.shortcutbadger.ShortcutBadger;

public class Notification extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    private NotificationAdapter news_adapter;
    private List<Notification_Model> news_model;
    private RecyclerView recyclerView;
    private ShimmerFrameLayout mShimmerViewContainer;
    private RegularTextView placeholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_notification);
        placeholder = (RegularTextView) findViewById(R.id.placeholder);
//        Intent i = null;
//        Bundle args = i.getExtras();
//        if (args != null) {
//
//            Log.v("DATESA", i.getAction());
//
//        }
        mShimmerViewContainer = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        init();

        if (sharedpreferences.contains("NOTIFICATION")) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.remove("NOTIFICATION");
            editor.commit();
            ShortcutBadger.removeCount(this);
        }

//        ShortcutBadger.applyCount(context, cont); //for 1.1.4+
//        ShortcutBadger.removeCount(this);
    }

    private void init() {
        news_model = new ArrayList<>();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(3), false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        news_adapter = new NotificationAdapter(this, news_model);
        recyclerView.setAdapter(news_adapter);

        GetResponse();
    }

    private void GetResponse() {

        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmer();
        news_model.clear();
        String lang = sharedpreferences.getString("LOCALES", "en");
        String USERID = sharedpreferences.getString("USER_ID", "");

        final String URL = Constants.BaseURL + "GET_NOTIFICATION?sop=" + Constants.SOP + "&lang=" + lang;
        Log.i("refresh", String.valueOf(URL));
        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("refresh", String.valueOf(response));
//                        progressBar.setVisibility(View.GONE);


                        JSONObject ar = null;
                        try {
                            ar = response.getJSONObject("result");
                            String sts = ar.getString("status");

                            if (sts.equals("1")) {
                                placeholder.setVisibility(View.GONE);
                                recyclerView.setAdapter(null);
                                recyclerView.removeAllViewsInLayout();
                                recyclerView.swapAdapter(news_adapter, true);
                                news_adapter.notifyDataSetChanged();

//                                JSONArray arst = ar.getJSONArray("data");

                                JSONArray storedata = ar.getJSONArray("data");
                                for (int i = 0; i < storedata.length(); i++) {
                                    JSONObject obj = storedata.getJSONObject(i);
                                    Notification_Model datas = new Notification_Model(obj.getString("TB_NAME"), obj.getString("TB_DESC"), obj.getString("TB_DATE"), obj.getString("TB_ID"));
                                    news_model.add(datas);

                                }
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putInt("NOTIFICATION", storedata.length());
                                editor.apply();


                                mShimmerViewContainer.stopShimmer();
                                mShimmerViewContainer.setVisibility(View.GONE);
                                news_adapter.notifyDataSetChanged();

                            } else {
                                placeholder.setVisibility(View.VISIBLE);
                                String msg = ar.getString("msg");
                                placeholder.setText(msg);

//                                Toast.makeText(Notification.this, msg, Toast.LENGTH_LONG).show();
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

                    // Toast.makeText(this, "NetworkError ", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    //Toast.makeText(this, "ServerError" + error.toString(), Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {

                    //Toast.makeText(this, "AUTH", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {

//                    Toast.makeText(this, "PARSE ERROpR", Toast.LENGTH_LONG).show();
                } else if (error instanceof NoConnectionError) {

//                    Toast.makeText(this, "NO CONNECTION", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    GetResponse();

                }
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(request_json);
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

    public void backsass(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_stay, R.anim.activity_slide_to_bottom);
    }
}
