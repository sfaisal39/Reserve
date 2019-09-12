package io.approots.reserve.Activities.History;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import io.approots.reserve.CustomFont.SemiBoldTextView;
import io.approots.reserve.R;
import me.leolin.shortcutbadger.ShortcutBadger;

public class Hostory_main extends AppCompatActivity {

    SemiBoldTextView calender, reservation, history;
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
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        setContentView(R.layout.activity_hostory_main);
        reservation = (SemiBoldTextView) findViewById(R.id.reservation);
        calender = (SemiBoldTextView) findViewById(R.id.calender);
        history = (SemiBoldTextView) findViewById(R.id.history);

        if (sharedpreferences.contains("ORDER")) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.remove("ORDER");
            editor.apply();
//            ShortcutBadger.removeCount(this);
        }


        replaceFragment(new FragmentReservation(), "Reservation");

        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calender.setTextColor(getResources().getColor(R.color.colorAccent));
                reservation.setTextColor(getResources().getColor(R.color.colorGrey_text));
                history.setTextColor(getResources().getColor(R.color.colorGrey_text));
                replaceFragment(new FragmentCalendar(), "Calendar");

            }
        });

        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calender.setTextColor(getResources().getColor(R.color.colorGrey_text));
                reservation.setTextColor(getResources().getColor(R.color.colorAccent));
                history.setTextColor(getResources().getColor(R.color.colorGrey_text));
                replaceFragment(new FragmentReservation(), "Reservation");
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calender.setTextColor(getResources().getColor(R.color.colorGrey_text));
                reservation.setTextColor(getResources().getColor(R.color.colorGrey_text));
                history.setTextColor(getResources().getColor(R.color.colorAccent));
                replaceFragment(new FragmentHistory(), getString(R.string.history));
            }
        });


    }

    private void replaceFragment(Fragment targetFragment, String name) {

        View view = this.getCurrentFocus();
        FragmentManager fm = getFragmentManager();
        if (view != null) {

            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        fm.beginTransaction()
                .replace(R.id.container, targetFragment, "fragment")
                .addToBackStack(name)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
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

}
