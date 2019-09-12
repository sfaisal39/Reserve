package io.approots.reserve.Activities;

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

import io.approots.reserve.CustomFont.SemiBoldTextView;
import io.approots.reserve.R;

public class Reservation_Done extends AppCompatActivity {


    SemiBoldTextView textmessage;

    int cont = 0;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        setContentView(R.layout.activity_reservation__done);
        textmessage = (SemiBoldTextView) findViewById(R.id.textmessage);

//        if (sharedpreferences.contains("ORDER")) {
//            cont = sharedpreferences.getInt("ORDER", 0);
//        }
//        cont++;
//        SharedPreferences.Editor editor = sharedpreferences.edit();
//        editor.putInt("ORDER", cont);
//        editor.apply();
//

        Intent intent = getIntent();
        Bundle args = intent.getExtras();
        if (args != null) {
            if (args.containsKey("MSG")) {

                textmessage.setText(args.getString("MSG"));

            }
        }
    }

    public void backhome(View view) {
        startActivity(new Intent(Reservation_Done.this, Home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }
}
