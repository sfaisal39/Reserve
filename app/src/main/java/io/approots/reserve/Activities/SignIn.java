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

import io.approots.reserve.CustomFont.SemiBoldTextView;
import io.approots.reserve.R;
import io.approots.reserve.Utilites.Constants;
import io.approots.reserve.Utilites.MySingleton;

public class SignIn extends AppCompatActivity {

    private RelativeLayout res, login_button;
    private EditText email_edt, password_edt;
    private ProgressBar progresbar;

    SemiBoldTextView forgot_button;
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
        setContentView(R.layout.activity_sign_in);

        Intent intent = getIntent();
        Bundle args = intent.getExtras();
        if (args != null) {
            if (args.containsKey("From")) {

                From = args.getInt("From");

            }
        }
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        res = (RelativeLayout) findViewById(R.id.res);
        login_button = (RelativeLayout) findViewById(R.id.login_button);
        forgot_button = (SemiBoldTextView) findViewById(R.id.forgot_button);
        email_edt = (EditText) findViewById(R.id.email_edt);
        password_edt = (EditText) findViewById(R.id.password_edt);
        progresbar = (ProgressBar) findViewById(R.id.progresbar);

        Typeface type = Typeface.createFromAsset(getAssets(), "Cairo-Bold.ttf");
        email_edt.setTypeface(type);
        password_edt.setTypeface(type);

        email_edt.setText(sharedpreferences.getString("Login_mail", ""));
        password_edt.setText(sharedpreferences.getString("Login_password", ""));
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

                if (!email_edt.getText().toString().isEmpty() && !password_edt.getText().toString().isEmpty()) {
                    login_button.setEnabled(false);
                    Login_Api_Call();
                } else {
                    Toast.makeText(SignIn.this, "Field Required", Toast.LENGTH_SHORT).show();
                }

            }
        });

        forgot_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgot_button.setEnabled(false);
                startActivity(new Intent(SignIn.this, ForgetActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        forgot_button.setEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        forgot_button.setEnabled(true);
    }

    private void Login_Api_Call() {
        progresbar.setVisibility(View.VISIBLE);

        String lang = sharedpreferences.getString("LOCALES", "en");


        String URL = Constants.BaseURL + "GET_LOGIN?sop=" + Constants.SOP + "&lang=" + lang + "&email=" + email_edt.getText().toString().trim() + "&password=" + password_edt.getText().toString().trim() + "&UDID=" + FirebaseInstanceId.getInstance().getToken();

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
//                                JSONArray arst = ar.getJSONArray("data");
//                                JSONObject obj = arst.getJSONObject(0);
                                SharedPreferences.Editor edit = sharedpreferences.edit();
                                edit.putString("USER_ID", ar.getString("TB_ID"));
                                edit.putString("USER_NAME", ar.getString("TB_FNAME") + " " + ar.getString("TB_LNAME"));
                                edit.putString("USER_MOBILE", ar.getString("TB_MOBILE"));
//                                SharedPreferences.Editor edit = sharedpreferences.edit();
//                                edit.putString("TB_ID", ar.getString("TB_ID"));
//                                edit.putString("STABLE_ID", ar.getString("STABLE_ID"));
                                edit.putString("Login_mail", email_edt.getText().toString().trim());
                                edit.putString("Login_password", password_edt.getText().toString().trim());
                                edit.apply();

                                if (From == 0)
                                    startActivity(new Intent(SignIn.this, Home.class));
                                finish();


                            } else {

                                login_button.setEnabled(true);
                                progresbar.setVisibility(View.GONE);
                                String mskg = ar.getString("msg");
                                Toast.makeText(SignIn.this, String.valueOf(mskg), Toast.LENGTH_LONG).show();

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

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    public void signupclick(View view) {
        startActivity(new Intent(SignIn.this, SignUp.class).putExtra("From", 1));
    }

    public void help(View view) {

        startActivity(new Intent(this, CMS_Activity.class));
    }
}
