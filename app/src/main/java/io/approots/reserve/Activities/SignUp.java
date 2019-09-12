package io.approots.reserve.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import io.approots.reserve.R;
import io.approots.reserve.Utilites.Constants;
import io.approots.reserve.Utilites.MySingleton;

public class SignUp extends AppCompatActivity {


    private RelativeLayout res, button_signup;
    private EditText namef_edt, namel_edt, mobile_edt, email_edt, password_edt, repassword_edt;
    private ProgressBar progress;
    CheckBox term_checkbox;

    int From = 0;

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
        setContentView(R.layout.activity_sign_up);
        Intent intent = getIntent();
        Bundle args = intent.getExtras();
        if (args != null) {
            if (args.containsKey("From")) {

                From = args.getInt("From");

            }
        }
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        init();
    }

    private void init() {
        res = (RelativeLayout) findViewById(R.id.res);
        namef_edt = (EditText) findViewById(R.id.namef_edt);
        namel_edt = (EditText) findViewById(R.id.namel_edt);
        email_edt = (EditText) findViewById(R.id.email_edt);
        mobile_edt = (EditText) findViewById(R.id.mobile_edt);
        password_edt = (EditText) findViewById(R.id.password_edt);
        repassword_edt = (EditText) findViewById(R.id.repassword_edt);
        button_signup = (RelativeLayout) findViewById(R.id.signup_button);
        progress = (ProgressBar) findViewById(R.id.progresbar);
        term_checkbox = (CheckBox) findViewById(R.id.terms_chekbox);
        Typeface type = Typeface.createFromAsset(getAssets(), "Cairo-Bold.ttf");
        namef_edt.setTypeface(type);
        namel_edt.setTypeface(type);
        email_edt.setTypeface(type);
        mobile_edt.setTypeface(type);
        password_edt.setTypeface(type);
        repassword_edt.setTypeface(type);
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

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!namef_edt.getText().equals("") && !namef_edt.getText().equals("") && !email_edt.getText().equals("") && !mobile_edt.getText().equals("") && !password_edt.getText().equals("") && !repassword_edt.getText().equals("")) {

                    if (term_checkbox.isChecked()) {
                        Signup_Api_Call();
                    } else {
                        Toast.makeText(SignUp.this, getResources().getString(R.string.accept_terms_conditions), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(SignUp.this, getResources().getString(R.string.some_fieldis_empty), Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    private void Signup_Api_Call() {
        progress.setVisibility(View.VISIBLE);

        String lang = sharedpreferences.getString("LOCALES", "en");


//        FirebaseInstanceId.getInstance().getToken()

        String URL = Constants.BaseURL + "GET_SIGNUP?sop=" + Constants.SOP + "&lang=" + lang + "&TB_FNAME=" + namef_edt.getText().toString().trim() + "&TB_LNAME=" + namel_edt.getText().toString().trim() + "&TB_EMAIL=" + email_edt.getText().toString().trim() + "&TB_PHONE=" + mobile_edt.getText().toString().trim() + "&TB_PASSWORD=" + password_edt.getText().toString().trim() + "&RETYPE_PASSWORD=" + repassword_edt.getText().toString().trim() + "&TB_DEVICE=Android&TB_UDID=" + FirebaseInstanceId.getInstance().getToken();

        java.net.URL url = null;

        try {
            URL = String.valueOf(new URL(URL));
            URL = URL.replace(" ", "%20");
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        Log.i("SignUp", String.valueOf(URL));
        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("SignUp", String.valueOf(response));
                        try {

                            JSONObject ar = response.getJSONObject("result");
                            String sts = ar.getString("status");
                            String msg = ar.getString("msg");

                            if (sts.equals("1")) {


                                button_signup.setEnabled(true);
                                progress.setVisibility(View.GONE);

//                                JSONArray arst = ar.getJSONArray("data");
//                                JSONObject obj = arst.getJSONObject(0);
                                SharedPreferences.Editor edit = sharedpreferences.edit();
                                edit.putString("USER_ID", ar.getString("userid"));
//                                edit.putString("STABLE_ID", ar.getString("STABLE_ID"));
                                edit.putString("Login_mail", email_edt.getText().toString().trim());
                                edit.putString("Login_password", password_edt.getText().toString().trim());
                                edit.commit();

                                Toast.makeText(SignUp.this, String.valueOf(msg), Toast.LENGTH_LONG).show();
                                if (From == 0)
                                    startActivity(new Intent(SignUp.this, Home.class));

                                finish();


                            } else {

                                button_signup.setEnabled(true);
                                progress.setVisibility(View.GONE);

                                Toast.makeText(SignUp.this, String.valueOf(msg), Toast.LENGTH_LONG).show();

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

//                    Toast.makeText(getActivity(), "PARSE ERROpR", Toast.LENGTH_LONG).show();
                } else if (error instanceof NoConnectionError) {

//                    Toast.makeText(getActivity(), "NO CONNECTION", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
//                    SetGallery(subcat_id, pro_id);
                }
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(request_json);
    }

    public void backbbt(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void help(View view) {

        startActivity(new Intent(this,CMS_Activity.class));}
}
