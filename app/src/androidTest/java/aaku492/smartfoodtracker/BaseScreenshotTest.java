package aaku492.smartfoodtracker;

import android.support.test.InstrumentationRegistry;
import android.view.LayoutInflater;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-25.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public abstract class BaseScreenshotTest {
    protected LayoutInflater getLayoutInflater() {
        try {
            return LayoutInflater.from(InstrumentationRegistry.getTargetContext());
        } catch (NullPointerException e) {
            throw new IllegalStateException("The test runner setup is most likely messed up. " +
                    "Check the app module build.gradle and verify that the test runner is " + FITScreenshotTestRunner.class.getName(), e);
        }
    }
}
