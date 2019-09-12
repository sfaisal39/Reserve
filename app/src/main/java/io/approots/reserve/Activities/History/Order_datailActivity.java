package io.approots.reserve.Activities.History;

import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.jar.Attributes;

import io.approots.reserve.CustomFont.SemiBoldTextView;
import io.approots.reserve.Models.CalDay_List;
import io.approots.reserve.R;
import io.approots.reserve.Utilites.Constants;
import io.approots.reserve.Utilites.MySingleton;

public class Order_datailActivity extends AppCompatActivity {

    SemiBoldTextView status;

    EditText DateTime_ed, name_ed, mobile_ed, plate_no_ed, choose_branch_ed, car_doc_ed;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    String Type, ORDER_ID;
    RelativeLayout button_review;

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
        setContentView(R.layout.activity_order_datail);
        init();
    }

    private void init() {
        button_review = (RelativeLayout) findViewById(R.id.button_review);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        status = (SemiBoldTextView) findViewById(R.id.status);
        DateTime_ed = (EditText) findViewById(R.id.date_time);
        name_ed = (EditText) findViewById(R.id.name);
        mobile_ed = (EditText) findViewById(R.id.mobile);
        plate_no_ed = (EditText) findViewById(R.id.plate_no);
        choose_branch_ed = (EditText) findViewById(R.id.choose_branch);
        car_doc_ed = (EditText) findViewById(R.id.car_doc);

        Intent intent = getIntent();
        Bundle args = intent.getExtras();
        if (args != null) {
            if (args.containsKey("Tb_Type")) {

                Type = args.getString("Tb_Type");


                ORDER_ID = args.getString("ORDER_ID", "");
//                if (!Type.equals("D")) {
//                        ORDER_ID
                status.setText(args.getString("STATUS_LABEL", ""));
                choose_branch_ed.setText(args.getString("FULL_DATE", ""));
                DateTime_ed.setText(args.getString("CUST_NAME", ""));
                name_ed.setText(args.getString("Time", ""));
                mobile_ed.setText(args.getString("NAME", ""));
                plate_no_ed.setText(args.getString("PHONE", ""));

                if (args.getString("STATUS_LABEL", "").equals("Pending")) {
                    button_review.setVisibility(View.VISIBLE);
                }
//                car_doc_ed.setText(args.getString("FULL_DATE", ""));
//                }


            }
        }


    }

    public void backsas(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.activity_stay, R.anim.activity_slide_to_bottom);
    }

    public void cancel(View view) {


        Alertdialog();
    }

    private void GetCancel() {
        String lang = sharedpreferences.getString("LOCALES", "en");
        String USER_ID = sharedpreferences.getString("USER_ID", "");

        final String URL = Constants.BaseURL + "CANCEL_BOOKING?sop=" + Constants.SOP + "&lang=" + lang + "&TB_USERID=" + USER_ID + "&TB_ORDERID=" + ORDER_ID;
        Log.i("CHDS", String.valueOf(URL));
        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.GET, URL, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("CHDS", String.valueOf(response));
                        try {

                            JSONObject ar = response.getJSONObject("result");
                            String sts = ar.getString("status");
                            String msg = ar.getString("msg");

                            Toast.makeText(Order_datailActivity.this, msg, Toast.LENGTH_LONG).show();
                            if (sts.equals("1")) {


                                finish();
//                                JSONArray arst = ar.getJSONArray("data");
//

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
                    GetCancel();
                }
            }
        });
        MySingleton.getInstance(Order_datailActivity.this).addToRequestQueue(request_json);
    }


    void Alertdialog() {
        // custom dialog
        final Dialog dialog = new Dialog(this, R.style.NewDialog);
        dialog.setContentView(R.layout.confirmation);
        dialog.setCancelable(false);
        TextView mesage = dialog.findViewById(R.id.messagesa);
//        TextView eddor = dialog.findViewById(R.id.errorcode);
        TextView okbutton = dialog.findViewById(R.id.okbutton);
        TextView onbutton = dialog.findViewById(R.id.onbutton);
//        mesage.setText(message);
//        eddor.setVisibility(View.GONE);
//        eddor.setText("Error code: " + code);
        onbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();


            }
        });
        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dialog.dismiss();
                GetCancel();

            }
        });

        dialog.show();
    }

}
