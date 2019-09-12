package io.approots.reserve.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import io.approots.reserve.R;
import io.approots.reserve.Utilites.Constants;
import io.approots.reserve.Utilites.MySingleton;

public class ForgetActivity extends AppCompatActivity {

    private RelativeLayout res, login_button;
    private EditText email_edt;

    private ProgressBar progresbar;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";

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
        setContentView(R.layout.activity_forget);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        email_edt = (EditText) findViewById(R.id.email_edt);
        res = (RelativeLayout) findViewById(R.id.res);
        login_button = (RelativeLayout) findViewById(R.id.login_button);
        progresbar = (ProgressBar) findViewById(R.id.progresbar);
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View vis = getCurrentFocus();
                if (vis != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(vis.getWindowToken(), 0);
                }
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!email_edt.getText().toString().isEmpty()) {
                    login_button.setEnabled(false);
                    Login_Api_Call();
                } else {
                    Toast.makeText(ForgetActivity.this, "Field Required", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void Login_Api_Call() {

        progresbar.setVisibility(View.VISIBLE);

        String lang = sharedpreferences.getString("LOCALES", "en");


        String URL = Constants.BaseURL + "FORGOTE_PASS?lang=" + lang + "&email=" + email_edt.getText().toString().trim();

        java.net.URL url = null;

        try {
            URL = String.valueOf(new URL(URL));
            URL = URL.replace(" ", "%20");
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        Log.i("Login", String.valueOf(URL));
        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Login", String.valueOf(response));
                        try {

                            JSONObject ar = response.getJSONObject("result");
                            String sts = ar.getString("status");
                            String msg = ar.getString("msg");
                            login_button.setEnabled(true);
                            if (sts.equals("1")) {

//                                JSONArray arst = ar.getJSONArray("data");

                                progresbar.setVisibility(View.GONE);

                                finish();


                            } else {

                                login_button.setEnabled(true);
                                progresbar.setVisibility(View.GONE);
                                String mskg = ar.getString("msg");
                                Toast.makeText(ForgetActivity.this, String.valueOf(mskg), Toast.LENGTH_LONG).show();

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
                    Login_Api_Call();
                    // Toast.makeText(getActivity(), "NetworkError ", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    //Toast.makeText(getActivity(), "ServerError" + error.toString(), Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {

                    //Toast.makeText(getActivity(), "AUTH", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {

//                    Toast.makeText(getActivity(), "PARSE ERROpR", Toast.LENGTH_LONG).show();
                } else if (error instanceof NoConnectionError) {


//                    Toast.makeText(getActivity(), "NO CONNECTION", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
//                    SetGallery(subcat_id, pro_id);
                    Login_Api_Call();
                }
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(request_json);

    }
}

