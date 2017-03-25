package aaku492.smartfoodtracker;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;

import com.facebook.testing.screenshot.ScreenshotRunner;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-20.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class FITScreenshotTestRunner extends AndroidJUnitRunner {
    @Override
    public void onCreate(Bundle args) {
        ScreenshotRunner.onCreate(this, args);
        super.onCreate(args);
    }

    @Override
    public void finish(int resultCode, Bundle results) {
        ScreenshotRunner.onDestroy();
        super.finish(resultCode, results);
    }
}
