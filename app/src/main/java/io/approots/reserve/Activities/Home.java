package io.approots.reserve.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.jaeger.library.StatusBarUtil;
import com.rd.PageIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.approots.reserve.Activities.History.Hostory_main;
import io.approots.reserve.Adapters.HomeBanner_Adapter;
import io.approots.reserve.CustomFont.RegularTextView;
import io.approots.reserve.Models.Home_Banner;
import io.approots.reserve.R;
import io.approots.reserve.Utilites.Constants;
import io.approots.reserve.Utilites.KKViewPager;
import io.approots.reserve.Utilites.MySingleton;

public class Home extends AppCompatActivity {

    private List<Home_Banner> data_banner;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    HomeBanner_Adapter adapter_banner;
    PageIndicatorView pageIndicatorView;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    private ProgressBar progressBar;

    RelativeLayout icon_history, iocn_chat, icon_notification;
    RegularTextView history, chat, notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        } else {
//
//            requestWindowFeature(Window.FEATURE_NO_TITLE);
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }
        setContentView(R.layout.activity_home);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        icon_history = (RelativeLayout) findViewById(R.id.icon_history);
        iocn_chat = (RelativeLayout) findViewById(R.id.iocn_chat);
        icon_notification = (RelativeLayout) findViewById(R.id.icon_notification);

        history = (RegularTextView) findViewById(R.id.textmenu_h);
        chat = (RegularTextView) findViewById(R.id.textchat_h);
        notification = (RegularTextView) findViewById(R.id.textnotification_h);


        data_banner = new ArrayList<>();
        mPager = (ViewPager) findViewById(R.id.pager);
        progressBar = (ProgressBar) findViewById(R.id.progresbar);
        pageIndicatorView = findViewById(R.id.pageIndicatorView);


        adapter_banner = new HomeBanner_Adapter(Home.this, data_banner);
        mPager.setAdapter(adapter_banner);
//        mPager.setAnimationEnabled(true);
//        mPager.setFadeEnabled(true);
//        mPager.setFadeFactor(0.7f);


//        mPager.setPageTransformer(false, new CardsPagerTransformerShift(12, 0, 0, 10));
        NUM_PAGES = data_banner.size();
        pageIndicatorView.setCount(data_banner.size());
        // Pager listener over indicator
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {/*empty*/}

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {/*empty*/}
        });
        GetResponse();
//        GetResponse();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Checkstatus();
//        Log.d("USERID", "RESTART");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Checkstatus();
//        Log.d("USERID", "RESUME");
    }

    private void Checkstatus() {

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//        Log.d("USERID", sharedpreferences.getString("USER_ID", "no"));
        if (sharedpreferences.contains("USER_ID")) {
            icon_history.setVisibility(View.VISIBLE);
            iocn_chat.setVisibility(View.VISIBLE);
            icon_notification.setVisibility(View.VISIBLE);

            if (sharedpreferences.contains("NOTIFICATION")) {
//                Log.d("USERID", String.valueOf(sharedpreferences.getInt("NOTIFICATION", 0)));
                notification.setText(String.valueOf(sharedpreferences.getInt("NOTIFICATION", 0)));
            } else {
                notification.setText(String.valueOf(0));
            }
            if (sharedpreferences.contains("ORDER")) {
                history.setText(String.valueOf(sharedpreferences.getInt("ORDER", 0)));
            } else {
                history.setText(String.valueOf(0));
            }

        } else {
            icon_history.setVisibility(View.GONE);
            iocn_chat.setVisibility(View.GONE);
            icon_notification.setVisibility(View.GONE);
        }
    }


    private void GetResponse() {
        progressBar.setVisibility(View.VISIBLE);
        String lang = sharedpreferences.getString("LOCALES", "en");
        String USER_ID = sharedpreferences.getString("USER_ID", "");
        final String URL = Constants.BaseURL + "GET_HOME?sop=" + Constants.SOP + "&lang=" + lang + "&TB_USERID=" + USER_ID;

        Log.i("RefreshHOME", String.valueOf(URL));
        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("RefreshHOME", String.valueOf(response));
                        progressBar.setVisibility(View.GONE);


                        JSONObject ar = null;
                        try {
                            ar = response.getJSONObject("result");
                            String sts = ar.getString("status");

                            if (sts.equals("1")) {
                                JSONArray data_banners = ar.getJSONArray("data");


                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putInt("ORDER", Integer.parseInt(ar.getString("tot_order")));
                                editor.putInt("NOTIFICATION", Integer.parseInt(ar.getString("tot_notify")));
                                editor.apply();

                                history.setText(ar.getString("tot_order"));
                                notification.setText(ar.getString("tot_notify"));


//                                JSONArray data_banners = ar.getJSONArray("data");
                                for (int i = 0; i < data_banners.length(); i++) {
                                    JSONObject obj = data_banners.getJSONObject(i);
                                    Home_Banner datas = new Home_Banner(obj.getString("TB_NAME"), obj.getString("TB_IMG"), obj.getString("TB_TYPE"), obj.getString("TB_ID"));
                                    data_banner.add(datas);
                                    adapter_banner.notifyDataSetChanged();

                                }
                            } else {
                                String msg = ar.getString("msg");
                                Toast.makeText(Home.this, msg, Toast.LENGTH_LONG).show();
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

    public void history(View view) {

        if (sharedpreferences.contains("USER_ID")) {

            Intent in = new Intent(this, Hostory_main.class);
            startActivity(in);
            overridePendingTransition(R.anim.activity_slide_from_bottom, R.anim.activity_stay);
        } else {

            startActivity(new Intent(this, SignIn.class).putExtra("From", 1));
        }
    }

    void GetTOT() {
        String lang = sharedpreferences.getString("LOCALES", "en");
        String TB_USERID = sharedpreferences.getString("USER_ID", "");

        final String URL = Constants.BaseURL + "GET_TOT?sop=" + Constants.SOP + "&lang=" + lang + "&TB_USERID=" + TB_USERID + "&CLINIC_TB_ID=";

        Log.i("RefreshHOME", String.valueOf(URL));
        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("RefreshHOME", String.valueOf(response));


                        JSONObject ar = null;
                        try {
                            ar = response.getJSONObject("result");
                            String sts = ar.getString("status");

                            if (sts.equals("1")) {

//                                appointment_text.setText(ar.getString("TOT_APPOINTMENT"));
//                                notification.setText(ar.getString("TOT_NOTIFICATION"));
//                                history.setText(ar.getString("TOT_SUMMARY"));
//                                chat.setText("0");
                            } else {


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                NetworkResponse networkResponse = error.networkResponse;


                if (error instanceof NetworkError) {
                    GetTOT();
                } else if (error instanceof ServerError) {
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                    GetTOT();
                } else if (error instanceof TimeoutError) {
                    GetTOT();

                }
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(request_json);
    }

    public void notification(View view) {
        Intent in = new Intent(this, Notification.class);
        startActivity(in);
        overridePendingTransition(R.anim.activity_slide_from_bottom, R.anim.activity_stay);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Checkstatus();
        Log.d("USERID", "STR");
    }

//    @Override
//    protected void onStop() {
//
//        Checkstatus();
//        Log.d("USERID", "STOP");
////        super.onStop();
//    }

    public void logout(View view) {
//        if (sharedpreferences.contains("USER_ID")) {
//            SharedPreferences.Editor edit = sharedpreferences.edit();
//            edit.remove("USER_ID");
//            edit.commit();
//        }
        startActivity(new Intent(this, Splash_Screen.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }
//    private void getRemoveUser() {
//
//    }
}
