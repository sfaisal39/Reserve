package io.approots.reserve.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.analytics.FirebaseAnalytics;

import io.approots.reserve.R;
import me.leolin.shortcutbadger.ShortcutBadger;

public class Splash_Screen extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Intent intent = getIntent();
        Bundle args = intent.getExtras();
        Log.v("intent_URI", intent.toUri(0));

//        int badgeCount = 30;
//        ShortcutBadger.removeCount(this);
//        ShortcutBadger.applyCount(getApplicationContext(), badgeCount); //for 1.1.4+

        get_continue();
    }

    public void get_continue() {
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {


                Intent i = new Intent(Splash_Screen.this, MainActivity.class);
                startActivity(i);
                finish();


            }
        }, 2000);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle args = intent.getExtras();
        Log.v("intent_URI", "NEWINTEMT :" + args + " :" + intent.getAction());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
