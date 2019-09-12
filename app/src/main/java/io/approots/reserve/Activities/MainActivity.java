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
import com.google.firebase.iid.FirebaseInstanceId;
import com.jaeger.library.StatusBarUtil;
import com.rd.PageIndicatorView;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.approots.reserve.Adapters.Adoption_Adapter;
import io.approots.reserve.Adapters.HomeBanner_Adapter;
import io.approots.reserve.Models.AdOption_Model;
import io.approots.reserve.Models.Home_Banner;
import io.approots.reserve.R;
import io.approots.reserve.Utilites.Constants;
import io.approots.reserve.Utilites.KKViewPager;
import io.approots.reserve.Utilites.MySingleton;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    private List<AdOption_Model> data_banner;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    Adoption_Adapter adapter_banner;
    CirclePageIndicator pageIndicatorView;

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
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        data_banner = new ArrayList<>();
        mPager = (ViewPager) findViewById(R.id.pager);
        pageIndicatorView = findViewById(R.id.pageIndicatorView);


        adapter_banner = new Adoption_Adapter(MainActivity.this, data_banner);
        mPager.setAdapter(adapter_banner);

        pageIndicatorView.setViewPager(mPager);

        NUM_PAGES = data_banner.size();
//        pageIndicatorView.setCount(data_banner.size());


        pageIndicatorView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {/*empty*/}

            @Override
            public void onPageSelected(int position) {
//                pageIndicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {/*empty*/}
        });

        // Pager listener over indicator
//        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {/*empty*/}
//
//            @Override
//            public void onPageSelected(int position) {
//                pageIndicatorView.setSelection(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {/*empty*/}
//        });
        GetResponse();

        SetUDID();

    }

    private void getRemoveUser() {
        if (sharedpreferences.contains("USER_ID")) {
            SharedPreferences.Editor edit = sharedpreferences.edit();
            edit.remove("USER_ID");
            edit.commit();
        }
    }

    private void GetResponse() {

        final String URL = Constants.BaseURL + "GET_ADS_OPTION?sop=" + Constants.SOP;

        Log.i("RefreshHOME", String.valueOf(URL));
        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("RefreshHOME", String.valueOf(response));
//                        progressBar.setVisibility(View.GONE);


                        JSONObject ar = null;
                        try {
                            ar = response.getJSONObject("result");
                            String sts = ar.getString("status");

                            if (sts.equals("1")) {
                                JSONArray data_banners = ar.getJSONArray("data");


//                                JSONArray data_banners = ar.getJSONArray("data");
                                for (int i = 0; i < data_banners.length(); i++) {
                                    JSONObject obj = data_banners.getJSONObject(i);
                                    AdOption_Model datas = new AdOption_Model(obj.getString("TB_ID"), obj.getString("TB_WEB"), obj.getString("TB_IMG"), obj.getString("TB_DATE"));
                                    data_banner.add(datas);
                                    adapter_banner.notifyDataSetChanged();
                                }
                            } else {
                                String msg = ar.getString("msg");
                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
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

    public void signin(View view) {
        startActivity(new Intent(MainActivity.this, SignIn.class));
//        finish();
    }

    public void signup(View view) {
        startActivity(new Intent(MainActivity.this, SignUp.class));
//        finish();
    }

    public void guest(View view) {
        getRemoveUser();
        startActivity(new Intent(MainActivity.this, Home.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void SetUDID() {


//        String TB_CUSTID = sharedpreferences.getString("TB_CUSTID", "");
//        GET_UDID?UDID=string&Type=string
        final String URL = Constants.BaseURL + "GET_UDID?UDID=" + FirebaseInstanceId.getInstance().getToken() + "&Type=Android";
//                + FirebaseInstanceId.getInstance().getToken() + "&Type=Android&UPDATE=" + update;

//        Toast.makeText(getActivity(), sharedpreferences.getString("ParentID", ""), Toast.LENGTH_LONG).show();
        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Called", String.valueOf(response));
                        Log.i("NOTIFICTION_FUN", String.valueOf(FirebaseInstanceId.getInstance().getToken()));
//                        Toast.makeText(getActivity(),String.valueOf(response),Toast.LENGTH_LONG).show();
                        try {
                            Boolean as = null;
                            JSONObject ar = response.getJSONObject("result");
                            String sts = ar.getString("status");
                            String msg = ar.getString("msg");
//                            String data = ar.getString("data");
                            if (sts.equals("1")) {

//                                JSONArray arst = ar.getJSONArray("data");


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
                }
            }
        });
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(request_json);

    }

    @Override
    protected void onStart() {
        super.onStart();
//        getRemoveUser();
    }
}
