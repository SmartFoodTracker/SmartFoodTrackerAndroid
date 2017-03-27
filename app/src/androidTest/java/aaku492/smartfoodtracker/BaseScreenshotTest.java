package aaku492.smartfoodtracker;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;

import com.facebook.testing.screenshot.Screenshot;
import com.facebook.testing.screenshot.ViewHelpers;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-25.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public abstract class BaseScreenshotTest {
    private final ContextThemeWrapper context;

    public BaseScreenshotTest() {
        this.context = new ContextThemeWrapper(InstrumentationRegistry.getTargetContext(), R.style.AppTheme);
    }

    protected LayoutInflater getLayoutInflater() {
        try {
            // Need to manually specify the context to use the App's theme (which works with the design support library)
            // See: https://github.com/facebook/screenshot-tests-for-android/issues/32
            return LayoutInflater.from(context);
        } catch (NullPointerException e) {
            throw new IllegalStateException("The test runner setup is most likely messed up. " +
                    "Check the app module build.gradle and verify that the test runner is " + FITScreenshotTestRunner.class.getName(), e);
        }
    }

    protected void runOnMainSync(Runnable runnable) {
        // This is required for certain inflating/rendering/UI manipulation actions that might only be
        // supported on the main thread/looper threads (e.g. animations).
        // See the related issue: https://github.com/facebook/screenshot-tests-for-android/issues/57
        InstrumentationRegistry.getInstrumentation().runOnMainSync(runnable);
    }

    protected void takeScreenshot(View view) {
        takeScreenshot(view, null, null);
    }

    protected void takeScreenshot(View view, Integer widthDp) {
        takeScreenshot(view, widthDp, null);
    }

    protected void takeScreenshot(View view, @Nullable Integer widthDp, @Nullable Integer heightDp) {
        // Hack to fix a timing issue with the animations.
        // See: https://github.com/facebook/screenshot-tests-for-android/issues/60
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ViewHelpers helpers = ViewHelpers.setupView(view);
        if (widthDp != null) {
            helpers = helpers.setExactWidthDp(widthDp);
        }
        if (heightDp != null) {
            helpers = helpers.setExactHeightDp(heightDp);
        }
        helpers.layout();

        Screenshot.snap(view)
                .record();
    }

    protected Context getContext() {
        return context;
    }
}
