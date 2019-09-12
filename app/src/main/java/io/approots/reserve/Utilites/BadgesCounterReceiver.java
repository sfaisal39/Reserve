package io.approots.reserve.Utilites;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import me.leolin.shortcutbadger.ShortcutBadger;

public class BadgesCounterReceiver extends BroadcastReceiver {

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    int cont = 0;

    @Override
    public void onReceive(Context context, Intent intent) {

        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (sharedpreferences.contains("NOTIFICATION")) {
            cont = sharedpreferences.getInt("NOTIFICATION", 0);
        }
        cont++;
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("NOTIFICATION", cont);
        editor.apply();
        ShortcutBadger.applyCount(context, cont); //for 1.1.4+

//        Toast.makeText(context, "1203", Toast.LENGTH_LONG).show();
//        throw new UnsupportedOperationException("Not yet implemented");

    }


}
