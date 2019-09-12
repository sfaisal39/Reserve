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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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

import io.approots.reserve.Activities.History.Hostory_main;
import io.approots.reserve.Adapters.HomeDetail_Adapter;
import io.approots.reserve.CustomFont.RegularTextView;
import io.approots.reserve.Models.HomeDetail_Model;
import io.approots.reserve.R;
import io.approots.reserve.Utilites.Constants;
import io.approots.reserve.Utilites.MySingleton;

public class Home_Details extends AppCompatActivity {

    String TB_ID, Type, Name1;
    RegularTextView type_text;

    private RecyclerView RecyclerView;
    private HomeDetail_Adapter news_adapter;
    private List<HomeDetail_Model> news_model;
    private ProgressBar progressBar;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    private ShimmerFrameLayout mShimmerViewContainer;
    RelativeLayout icon_history, iocn_chat, icon_notification;
    RegularTextView textnotification, textmenu;

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
        setContentView(R.layout.activity_home__details);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        icon_history = (RelativeLayout) findViewById(R.id.icon_history);
        iocn_chat = (RelativeLayout) findViewById(R.id.iocn_chat);
        icon_notification = (RelativeLayout) findViewById(R.id.icon_notification);
        textnotification = (RegularTextView) findViewById(R.id.textnotification);
        textmenu = (RegularTextView) findViewById(R.id.textmenu);

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        type_text = (RegularTextView) findViewById(R.id.type_text);
        Intent intent = getIntent();
        Bundle args = intent.getExtras();
        if (args != null) {
            if (args.containsKey("TB_ID")) {
                TB_ID = args.getString("TB_ID");
                Type = args.getString("Tb_Type");
                type_text.setText(args.getString("TB_NAME") + " X");
                Name1 = args.getString("TB_NAME") + " X";


            }
        }
        Checkstatus();
        init();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Checkstatus();
    }

    private void Checkstatus() {

//        Log.d("USERID", sharedpreferences.getString("USER_ID", "no"));
        if (sharedpreferences.contains("USER_ID")) {
            icon_history.setVisibility(View.VISIBLE);
            iocn_chat.setVisibility(View.VISIBLE);
            icon_notification.setVisibility(View.VISIBLE);

            if (sharedpreferences.contains("NOTIFICATION")) {
                textnotification.setText(String.valueOf(sharedpreferences.getInt("NOTIFICATION", 0)));
            } else {
                textnotification.setText(String.valueOf(0));
            }
            if (sharedpreferences.contains("ORDER")) {
                textmenu.setText(String.valueOf(sharedpreferences.getInt("ORDER", 0)));
            } else {
                textmenu.setText(String.valueOf(0));
            }


        } else {
            icon_history.setVisibility(View.GONE);
            iocn_chat.setVisibility(View.GONE);
            icon_notification.setVisibility(View.GONE);
        }
    }

    private void init() {
        news_model = new ArrayList<>();
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        RecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);

        RecyclerView.setLayoutManager(new LinearLayoutManager(Home_Details.this, LinearLayoutManager.VERTICAL, false));
        RecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(3), false));
        RecyclerView.setItemAnimator(new DefaultItemAnimator());
        news_adapter = new HomeDetail_Adapter(Home_Details.this, news_model, Name1, Type);
        RecyclerView.setAdapter(news_adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        GetResponse();
        Checkstatus();
    }

    public void history(View view) {
        Intent in = new Intent(this, Hostory_main.class);
        startActivity(in);
        overridePendingTransition(R.anim.activity_slide_from_bottom, R.anim.activity_stay);
    }

    public void notification(View view) {
        Intent in = new Intent(this, Notification.class);
        startActivity(in);
        overridePendingTransition(R.anim.activity_slide_from_bottom, R.anim.activity_stay);
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

    public void back(View view) {
        onBackPressed();
    }

    private void GetResponse() {

        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmer();
        news_model.clear();


        progressBar.setVisibility(View.GONE);
        String lang = sharedpreferences.getString("LOCALES", "en");


        final String URL = Constants.BaseURL + "GET_HOME_DETAIL?sop=" + Constants.SOP + "&lang=" + lang + "&CATID=" + TB_ID;
        Log.i("refresh", String.valueOf(URL));
        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("refresh", String.valueOf(response));
                        progressBar.setVisibility(View.GONE);


                        JSONObject ar = null;
                        try {
                            ar = response.getJSONObject("result");
                            String sts = ar.getString("status");

                            if (sts.equals("1")) {
                                RecyclerView.setAdapter(null);
                                RecyclerView.removeAllViewsInLayout();
                                RecyclerView.swapAdapter(news_adapter, true);
                                news_adapter.notifyDataSetChanged();

                                JSONArray arst = ar.getJSONArray("data");

                                JSONArray storedata = ar.getJSONArray("data");
                                for (int i = 0; i < storedata.length(); i++) {
                                    JSONObject obj = storedata.getJSONObject(i);
                                    HomeDetail_Model datas = new HomeDetail_Model(obj.getString("TB_NAME"), obj.getString("TB_DESC"), obj.getString("TB_IMG"), obj.getString("TB_ID"));
                                    news_model.add(datas);

                                }

                                mShimmerViewContainer.stopShimmer();
                                mShimmerViewContainer.setVisibility(View.GONE);
                                news_adapter.notifyDataSetChanged();

                            } else {
                                String msg = ar.getString("msg");
                                Toast.makeText(Home_Details.this, msg, Toast.LENGTH_LONG).show();
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

    public void logout(View view) {
//        getRemoveUser();
//        startActivity(new Intent(this,Splash_Screen.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
//        finish();
    }
//    private void getRemoveUser() {
//        if (sharedpreferences.contains("USER_ID")) {
//            SharedPreferences.Editor edit = sharedpreferences.edit();
//            edit.remove("USER_ID");
//            edit.apply();
//        }
//    }


}
