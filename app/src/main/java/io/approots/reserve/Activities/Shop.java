package io.approots.reserve.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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
import io.approots.reserve.R;

public class Shop extends FragmentActivity implements OnMapReadyCallback {

    String TB_ID, Type, Name1, Name2, Name3;
    public static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    RegularTextView type_text, type_text2, type_text3, address_text, phone_text, aboout_text, appointment_text;
    private ImageView image;
    private SemiBoldTextView name_text, aboout;

    String[] perms = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};
    int permsRequestCode = 1;

    Double Long, Lat;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    private List<Branch_Model> branch_model;

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
        setContentView(R.layout.activity_shop);
        requestPermissionsPEr();
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        branch_model = new ArrayList<>();
        type_text = (RegularTextView) findViewById(R.id.type_text);
        type_text2 = (RegularTextView) findViewById(R.id.type_text2);
        type_text3 = (RegularTextView) findViewById(R.id.type_text3);
        address_text = (RegularTextView) findViewById(R.id.address_text);
        phone_text = (RegularTextView) findViewById(R.id.phone_text);
        image = (ImageView) findViewById(R.id.image);
        name_text = (SemiBoldTextView) findViewById(R.id.name_text);
        appointment_text = (RegularTextView) findViewById(R.id.appointment_text);
        aboout = (SemiBoldTextView) findViewById(R.id.about);
        aboout_text = (RegularTextView) findViewById(R.id.about_text);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        icon_history = (RelativeLayout) findViewById(R.id.icon_history);
        iocn_chat = (RelativeLayout) findViewById(R.id.iocn_chat);
        icon_notification = (RelativeLayout) findViewById(R.id.icon_notification);
        history = (RegularTextView) findViewById(R.id.textmenu);
        chat = (RegularTextView) findViewById(R.id.textchat);
        notification = (RegularTextView) findViewById(R.id.textnotification);
        Checkstatus();


        Intent intent = getIntent();
        Bundle args = intent.getExtras();
        if (args != null) {
            if (args.containsKey("TB_ID")) {
                TB_ID = args.getString("TB_ID");
                Type = args.getString("Tb_Type");
                type_text.setText(args.getString("TB_NAME"));
                type_text2.setText(args.getString("TB_NAME2"));
                type_text3.setText(args.getString("TB_NAME3") + " X");

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
                if (!args.getString("TB_LONG").equals("")) {
                    Long = Double.valueOf(args.getString("TB_LONG"));
                    Lat = Double.valueOf(args.getString("TB_LATI"));
                } else {
                    Long = 0.0;
                    Lat = 0.0;
                }

                name_text.setText(args.getString("TB_NAME3"));
                address_text.setText(args.getString("TB_ADDRESS"));
                phone_text.setText(args.getString("TB_PHONE1"));
                appointment_text.setText(args.getString("TB_PHONE2"));
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
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Checkstatus();
    }

    private void Checkstatus() {

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(perms, permsRequestCode);
            return;
        }


        for (int i = 0; i < branch_model.size(); i++) {

//            Log.v("LLL",i);
            if (!branch_model.get(i).getTB_LATI().isEmpty() && !branch_model.get(i).getTB_LONG().isEmpty()) {

                Long = Double.valueOf(branch_model.get(i).getTB_LATI());
                Lat = Double.valueOf(branch_model.get(i).getTB_LATI());

            } else {
                Long = 0.0;
                Lat = 0.0;

            }
//            Toast.makeText(this, i, Toast.LENGTH_SHORT).show();
            LatLng placeLocation = new LatLng(Lat, Long); //Make them global
            Marker placeMarker = googleMap.addMarker(new MarkerOptions().position(placeLocation)
                    .title(branch_model.get(i).getTB_NAME()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(placeLocation));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(100), 1000, null);
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

    public void quick(View view) {
        startActivity(new Intent(this, TimeSlot.class).putExtra("NAME1", Name1).putExtra("NAME2", Name2).putExtra("NAME3", Name3).putExtra("TB_ID", TB_ID).putExtra("Tb_Type", "C").putExtra("CAR_BRANCHES", "Quick Service"));
    }

    public void main(View view) {
        startActivity(new Intent(this, TimeSlot.class).putExtra("NAME1", Name1).putExtra("NAME2", Name2).putExtra("NAME3", Name3).putExtra("TB_ID", TB_ID).putExtra("Tb_Type", "C").putExtra("CAR_BRANCHES", "Main"));
    }

    public void body(View view) {
        startActivity(new Intent(this, TimeSlot.class).putExtra("NAME1", Name1).putExtra("NAME2", Name2).putExtra("NAME3", Name3).putExtra("TB_ID", TB_ID).putExtra("Tb_Type", "C").putExtra("CAR_BRANCHES", "Body Shop"));
    }


    public void notification(View view) {
        Intent in = new Intent(this, Notification.class);
        startActivity(in);
        overridePendingTransition(R.anim.activity_slide_from_bottom, R.anim.activity_stay);
    }

    public void history(View view) {
        Intent in = new Intent(this, Hostory_main.class);
        startActivity(in);
        overridePendingTransition(R.anim.activity_slide_from_bottom, R.anim.activity_stay);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Checkstatus();
    }
}
