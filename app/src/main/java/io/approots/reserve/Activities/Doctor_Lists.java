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
import com.rd.PageIndicatorView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.approots.reserve.Activities.History.Hostory_main;
import io.approots.reserve.Adapters.DoctorBanner_Adapter;
import io.approots.reserve.Adapters.HomeBanner_Adapter;
import io.approots.reserve.CustomFont.RegularTextView;
import io.approots.reserve.Models.HomeDetail_Model;
import io.approots.reserve.Models.Home_Banner;
import io.approots.reserve.R;
import io.approots.reserve.Utilites.Constants;
import io.approots.reserve.Utilites.MySingleton;

public class Doctor_Lists extends AppCompatActivity {

    String TB_ID, Type, Name1, Name2, Name3;
    RegularTextView type_text, type_text2, type_text3;

    private List<HomeDetail_Model> data_banner;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ProgressBar progressBar;
    DoctorBanner_Adapter adapter_banner;
    PageIndicatorView pageIndicatorView;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    String TB_OtherID;
    RelativeLayout icon_history, iocn_chat, icon_notification;
    RegularTextView history, chat, notification;

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
        setContentView(R.layout.activity_doctor__lists);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        icon_history = (RelativeLayout) findViewById(R.id.icon_history);
        iocn_chat = (RelativeLayout) findViewById(R.id.iocn_chat);
        icon_notification = (RelativeLayout) findViewById(R.id.icon_notification);
        history = (RegularTextView) findViewById(R.id.textmenu);
        chat = (RegularTextView) findViewById(R.id.textchat);
        notification = (RegularTextView) findViewById(R.id.textnotification);

        type_text = (RegularTextView) findViewById(R.id.type_text);
        type_text2 = (RegularTextView) findViewById(R.id.type_text2);
        type_text3 = (RegularTextView) findViewById(R.id.type_text3);

        Checkstatus();

        Intent intent = getIntent();
        Bundle args = intent.getExtras();
        if (args != null) {
            if (args.containsKey("TB_ID")) {
                TB_ID = args.getString("TB_ID");
                Type = args.getString("Tb_Type");


                type_text.setText(args.getString("TB_NAME"));
                type_text2.setText(args.getString("TB_NAME2"));
                type_text3.setText(args.getString("TB_NAME3"));


                Name1 = args.getString("TB_NAME");
                Name2 = args.getString("TB_NAME2");
                Name3 = args.getString("TB_NAME3");


            }
        }


        data_banner = new ArrayList<>();
        mPager = (ViewPager) findViewById(R.id.pager);
        progressBar = (ProgressBar) findViewById(R.id.progresbar);
        pageIndicatorView = findViewById(R.id.pageIndicatorView);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        adapter_banner = new DoctorBanner_Adapter(Doctor_Lists.this, data_banner);
        mPager.setAdapter(adapter_banner);


        NUM_PAGES = data_banner.size();
        pageIndicatorView.setCount(data_banner.size());
        // Pager listener over indicator
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                /*empty*/


            }

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelection(position);
                TB_OtherID = data_banner.get(position).getTB_ID();
                Name3 = data_banner.get(position).getTB_NAME();
                Log.d("POS:", data_banner.get(position).getTB_ID());
            }

            @Override
            public void onPageScrollStateChanged(int state) {/*empty*/}
        });
        GetResponse();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Checkstatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Checkstatus();
    }

    private void Checkstatus() {

        if (sharedpreferences.contains("USER_ID")) {
            icon_history.setVisibility(View.VISIBLE);
            iocn_chat.setVisibility(View.VISIBLE);
            icon_notification.setVisibility(View.VISIBLE);
//

            if (sharedpreferences.contains("NOTIFICATION")) {
                notification.setText(String.valueOf(sharedpreferences.getInt("NOTIFICATION", 0)));
            } else {
                notification.setText(String.valueOf(0));
            }
            if (sharedpreferences.contains("ORDER")) {
                history.setText(String.valueOf(sharedpreferences.getInt("ORDER", 0)));
            }else {
                history.setText(String.valueOf(0));
            }
//            GetTOT();
        } else {
            icon_history.setVisibility(View.GONE);
            iocn_chat.setVisibility(View.GONE);
            icon_notification.setVisibility(View.GONE);
        }
    }

    public void backss(View view) {
        onBackPressed();
    }

    private void GetResponse() {
        progressBar.setVisibility(View.VISIBLE);
        String lang = sharedpreferences.getString("LOCALES", "en");

        final String URL = Constants.BaseURL + "GET_PROD_DTL?sop=" + Constants.SOP + "&lang=" + lang + "&TB_TYPE=" + Type + "&PROID=" + TB_ID;

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


//                                JSONArray data_banners = ar.getJSONArray("data");
                                for (int i = 0; i < data_banners.length(); i++) {
                                    JSONObject obj = data_banners.getJSONObject(i);
                                    HomeDetail_Model datas = new HomeDetail_Model(obj.getString("TB_NAME"), obj.getString("TB_DESC"), obj.getString("TB_IMG"), obj.getString("TB_ID"));
                                    data_banner.add(datas);
                                    adapter_banner.notifyDataSetChanged();
                                }
                                TB_OtherID = data_banner.get(0).getTB_ID();
                                Name3 = data_banner.get(0).getTB_NAME();
                                Log.d("POS:", data_banner.get(0).getTB_ID());
                            } else {
                                String msg = ar.getString("msg");
                                Toast.makeText(Doctor_Lists.this, msg, Toast.LENGTH_LONG).show();
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

    public void TimeSlot(View view) {
        startActivity(new Intent(Doctor_Lists.this, TimeSlot.class).putExtra("NAME1", Name1).putExtra("NAME2", Name2).putExtra("NAME3", Name3).putExtra("TB_ID", TB_OtherID).putExtra("Tb_Type", "D"));
    }

//    void GetTOT() {
//        String lang = sharedpreferences.getString("LOCALES", "en");
//        String TB_USERID = sharedpreferences.getString("USER_ID", "");
//
//        final String URL = Constants.BaseURL + "GET_TOT?sop=" + Constants.SOP + "&lang=" + lang + "&TB_USERID=" + TB_USERID + "&CLINIC_TB_ID=" + TB_ID;
//
//        Log.i("RefreshHOME", String.valueOf(URL));
//        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.GET, URL, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.i("RefreshHOME", String.valueOf(response));
//
//
//                        JSONObject ar = null;
//                        try {
//                            ar = response.getJSONObject("result");
//                            String sts = ar.getString("status");
//
//                            if (sts.equals("1")) {
//
////                                appointment_text.setText(ar.getString("TOT_APPOINTMENT"));
////                                notification.setText(ar.getString("TOT_NOTIFICATION"));
////                                history.setText(ar.getString("TOT_SUMMARY"));
////                                chat.setText("0");
//                            } else {
//
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//                NetworkResponse networkResponse = error.networkResponse;
//
//
//                if (error instanceof NetworkError) {
//                    GetTOT();
//                } else if (error instanceof ServerError) {
//                } else if (error instanceof AuthFailureError) {
//                } else if (error instanceof ParseError) {
//                } else if (error instanceof NoConnectionError) {
//                    GetTOT();
//                } else if (error instanceof TimeoutError) {
//                    GetTOT();
//
//                }
//            }
//        });
//        MySingleton.getInstance(this).addToRequestQueue(request_json);
//    }

    public void notification(View view) {
//        startActivity(new Intent(this,Notification.class));
        Intent in = new Intent(this, Notification.class);
        startActivity(in);
        overridePendingTransition(R.anim.activity_slide_from_bottom, R.anim.activity_stay);
    }

    public void history(View view) {
        Intent in = new Intent(this, Hostory_main.class);
        startActivity(in);
        overridePendingTransition(R.anim.activity_slide_from_bottom, R.anim.activity_stay);
    }
}
