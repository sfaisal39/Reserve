package io.approots.reserve.Activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.approots.reserve.Activities.History.Hostory_main;
import io.approots.reserve.CustomFont.RegularTextView;
import io.approots.reserve.CustomFont.SemiBoldTextView;
import io.approots.reserve.Models.Branch_Model;
import io.approots.reserve.Models.HomeDetail_Model;
import io.approots.reserve.R;
import io.approots.reserve.Utilites.Constants;
import io.approots.reserve.Utilites.MySingleton;

public class Clinic_details extends FragmentActivity implements OnMapReadyCallback {

    String TB_ID, Type, Name1, Name2, Name3;
    public static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    RegularTextView type_text, type_text2, type_text3, address_text, phone_text, aboout_text;
    private ImageView image;
    private SemiBoldTextView name_text, appointment_text, aboout;

    private List<Branch_Model> branch_model;

    Double Long, Lat;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
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
        setContentView(R.layout.activity_clinic_details);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        branch_model = new ArrayList<>();
        icon_history = (RelativeLayout) findViewById(R.id.icon_history);
        iocn_chat = (RelativeLayout) findViewById(R.id.iocn_chat);
        icon_notification = (RelativeLayout) findViewById(R.id.icon_notification);

        history = (RegularTextView) findViewById(R.id.textmenu);
        chat = (RegularTextView) findViewById(R.id.textchat);
        notification = (RegularTextView) findViewById(R.id.textnotification);


        requestPermissionsPEr();
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        type_text = (RegularTextView) findViewById(R.id.type_text);
        type_text2 = (RegularTextView) findViewById(R.id.type_text2);
        type_text3 = (RegularTextView) findViewById(R.id.type_text3);
        address_text = (RegularTextView) findViewById(R.id.address_text);
        phone_text = (RegularTextView) findViewById(R.id.phone_text);
        image = (ImageView) findViewById(R.id.image);
        name_text = (SemiBoldTextView) findViewById(R.id.name_text);
        appointment_text = (SemiBoldTextView) findViewById(R.id.appointment_text);
        aboout = (SemiBoldTextView) findViewById(R.id.about);
        aboout_text = (RegularTextView) findViewById(R.id.about_text);


        Intent intent = getIntent();
        Bundle args = intent.getExtras();
        if (args != null) {
            if (args.containsKey("TB_ID")) {
                TB_ID = args.getString("TB_ID");
                Type = args.getString("Tb_Type");
                type_text.setText(args.getString("TB_NAME"));
                type_text2.setText(args.getString("TB_NAME2"));
                type_text3.setText(args.getString("TB_NAME3") + " X");

                Log.v("DATAPRO", args.getString("DATA"));

                Checkstatus();
                try {
                    JSONArray array = new JSONArray(args.getString("DATA"));
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        Branch_Model ds = new Branch_Model(obj.getString("TB_NAME"), obj.getString("TB_ADDRESS"), obj.getString("TB_LATI"), obj.getString("TB_LONG"), obj.getString("TB_PHONE1"), obj.getString("TB_PHONE2"), obj.getString("TB_PHONE3"));
                        branch_model.add(ds);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (!args.getString("TB_LONG").isEmpty()) {


                    Long = Double.valueOf(args.getString("TB_LONG"));
                    Lat = Double.valueOf(args.getString("TB_LATI"));
                    Log.v("CHECK_LOC", args.getString("TB_LONG") + " = " + Long + "  : " + args.getString("TB_LATI") + " = " + Lat);

                } else {
                    Long = 0.0;
                    Lat = 0.0;

                    Log.v("CHECK_LOC", args.getString("TB_LONG") + " = " + Long + "  : " + args.getString("TB_LATI") + " = " + Lat);
                }

                name_text.setText(args.getString("TB_NAME3"));
                address_text.setText(args.getString("TB_ADDRESS"));
                phone_text.setText(args.getString("TB_PHONE1"));
                aboout_text.setText(args.getString("TB_ABOUT"));
                aboout.setText(getResources().getString(R.string.about) + " " + args.getString("TB_NAME3"));
//                appointment_text.setText(args.getString("TB_NAME"));

                Picasso.
                        with(this)
                        .load(args.getString("TB_IMG"))
                        .into(image);

                Name1 = args.getString("TB_NAME");
                Name2 = args.getString("TB_NAME2");
                Name3 = args.getString("TB_NAME3") + " X";


            }
        }


        //Calling button to open dialer
        phone_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!phone_text.getText().toString().trim().isEmpty()) {


                    //dial space.
                    Alertdialog();


                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Checkstatus();
        Log.d("STATUS", "RESSTRT");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Checkstatus();
        Log.d("STATUS", "RESUME");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Checkstatus();
        Log.d("STATUS", "POSTRESUME");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Checkstatus();
        Log.d("STATUS", "START");
    }

    private void Checkstatus() {

        Log.d("STATUS", sharedpreferences.getString("USER_ID", "NO"));
        if (sharedpreferences.contains("USER_ID")) {
            icon_history.setVisibility(View.VISIBLE);
            iocn_chat.setVisibility(View.VISIBLE);
            icon_notification.setVisibility(View.VISIBLE);
            if (sharedpreferences.contains("NOTIFICATION")) {
                Log.d("USERID", String.valueOf(sharedpreferences.getInt("NOTIFICATION", 0)));
                notification.setText(String.valueOf(sharedpreferences.getInt("NOTIFICATION", 0)));
            } else {
                notification.setText(String.valueOf(0));
            }
            if (sharedpreferences.contains("ORDER")) {
                history.setText(String.valueOf(sharedpreferences.getInt("ORDER", 0)));
            } else {
                history.setText(String.valueOf(0));
            }


            GetTOT();
        } else {
            icon_history.setVisibility(View.GONE);
            iocn_chat.setVisibility(View.GONE);
            icon_notification.setVisibility(View.GONE);
        }
    }

    public void backss(View view) {
        onBackPressed();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(perms, permsRequestCode);
            return;
        }

        for (int i = 0; i < branch_model.size(); i++) {

//            Log.v("LLL",i);
            if (!branch_model.get(i).getTB_LATI().isEmpty() && !branch_model.get(i).getTB_LONG().isEmpty()) {


                Long = Double.valueOf(branch_model.get(i).getTB_LONG());
                Lat = Double.valueOf(branch_model.get(i).getTB_LATI());

                if (!branch_model.get(i).getTB_LATI().isEmpty() && !branch_model.get(i).getTB_LONG().isEmpty()) {
                    LatLng myPosition = new LatLng(Lat, Long); //Make them global
                    CameraPosition position = new CameraPosition.Builder().
                            target(myPosition).zoom(11).bearing(60).tilt(30).build();

                    //_googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));

                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
                    googleMap.addMarker(new
                            MarkerOptions().position(myPosition).title(branch_model.get(i).getTB_NAME()));
                }
            } else {
                Long = 0.0;
                Lat = 0.0;

            }
//            Toast.makeText(this, i, Toast.LENGTH_SHORT).show();
//            LatLng placeLocation = new LatLng(Lat, Long); //Make them global
//            Marker placeMarker = googleMap.addMarker(new MarkerOptions().position(placeLocation)
//                    .title(branch_model.get(i).getTB_NAME()));
//
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(placeLocation));
//            googleMap.animateCamera(CameraUpdateFactory.zoomTo(120), 1200, null);


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mapFragment.getMapAsync(this);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
//                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
//                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void requestPermissionsPEr() {

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    public void doctorlist(View view) {

        startActivity(new Intent(Clinic_details.this, Doctor_Lists.class).putExtra("TB_ID", TB_ID).putExtra("Tb_Type", Type).putExtra("TB_NAME", type_text.getText().toString().trim()).putExtra("TB_NAME2", type_text2.getText().toString().trim()).putExtra("TB_NAME3", type_text3.getText().toString().trim()));
    }

    public void history(View view) {
//        startActivity(new Intent(this, Hostory_main.class));
        Intent in = new Intent(this, Hostory_main.class);
        startActivity(in);
        overridePendingTransition(R.anim.activity_slide_from_bottom, R.anim.activity_stay);
    }

    void GetTOT() {
        String lang = sharedpreferences.getString("LOCALES", "en");
        String TB_USERID = sharedpreferences.getString("USER_ID", "");

        final String URL = Constants.BaseURL + "GET_TOT?sop=" + Constants.SOP + "&lang=" + lang + "&TB_USERID=" + TB_USERID + "&CLINIC_TB_ID=" + TB_ID;

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

                                appointment_text.setText(ar.getString("TOT_APPOINTMENT"));
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

    void Alertdialog() {
        // custom dialog
        final Dialog dialog = new Dialog(this, R.style.NewDialog);
        dialog.setContentView(R.layout.confirmation_call);
        dialog.setCancelable(false);
        TextView mesage = dialog.findViewById(R.id.messagesa);
        TextView okbutton = dialog.findViewById(R.id.okbutton);
        TextView onbutton = dialog.findViewById(R.id.onbutton);

        mesage.setText(phone_text.getText().toString().trim());
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
//                GetCancel();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phone_text.getText().toString().trim()));
                startActivity(callIntent);

            }
        });

        dialog.show();
    }

}
