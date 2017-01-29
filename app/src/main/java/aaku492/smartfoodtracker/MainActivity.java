package aaku492.smartfoodtracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-28.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void setTitle(String title) {
        if (getSupportActionBar() == null) {
            Log.e(LOG_TAG, "You messed up! Action bar should've been present. Unable to set title to: " + title);
            return;
        }

        getSupportActionBar().setTitle(title);
    }
}
