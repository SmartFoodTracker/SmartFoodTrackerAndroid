package aaku492.smartfoodtracker;

import android.content.Context;
import android.support.annotation.NonNull;
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

    protected static void runOnMainSync(Runnable runnable) {
        // This is required for certain inflating/rendering/UI manipulation actions that might only be
        // supported on the main thread/looper threads (e.g. animations).
        // See the related issue: https://github.com/facebook/screenshot-tests-for-android/issues/57
        InstrumentationRegistry.getInstrumentation().runOnMainSync(runnable);
    }

    protected void takeScreenshot(@NonNull View view) {
        // The default that works for most cases
        takeScreenshot(view, ScreenshotTaker.DEFAULT_SCREENSHOT_WIDTH_DP, null);
    }

    protected void takeScreenshot(@NonNull View view, @Nullable Integer widthDp, @Nullable Integer heightDp) {
        ScreenshotTaker screenshotTaker = new ScreenshotTaker(view);
        if (widthDp != null) {
            screenshotTaker.setWidthDp(widthDp);
        }
        if (heightDp != null) {
            screenshotTaker.setHeightDp(heightDp);
        }
        screenshotTaker.setDelay(ScreenshotTaker.DEFAULT_DELAY_MS)
                .layout()
                .record();
    }

    protected Context getContext() {
        return context;
    }

    public static class ScreenshotTaker {
        public static final int DEFAULT_SCREENSHOT_WIDTH_DP = 300;
        public static final int DEFAULT_DELAY_MS = 100;

        private final ViewHelpers helpers;
        private final View view;
        @Nullable
        private Integer delayMilliseconds = null;

        public ScreenshotTaker(@NonNull View view) {
            this.helpers = ViewHelpers.setupView(view);
            this.view = view;
        }

        public ScreenshotTaker setWidthDp(@NonNull Integer widthDp) {
            helpers.setExactWidthDp(widthDp);
            return this;
        }

        public ScreenshotTaker setHeightDp(@NonNull Integer heightDp) {
            helpers.setExactHeightDp(heightDp);
            return this;
        }

        public ScreenshotTaker setDelay(@Nullable Integer delayMilliseconds) {
            this.delayMilliseconds = delayMilliseconds;
            return this;
        }

        public ScreenshotTaker layout(boolean onMainThread) {
            if (onMainThread) {
                runOnMainSync(new Runnable() {
                    @Override
                    public void run() {
                        layout();
                    }
                });
                return this;
            } else {
                return layout();
            }
        }

        public ScreenshotTaker layout() {
            if (delayMilliseconds != null) {
                // Hack to fix a timing issue with the animations.
                // See: https://github.com/facebook/screenshot-tests-for-android/issues/60
                try {
                    Thread.sleep(delayMilliseconds);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            helpers.layout();
            return this;
        }

        public void record() {
            Screenshot.snap(view).record();
        }
    }
}
