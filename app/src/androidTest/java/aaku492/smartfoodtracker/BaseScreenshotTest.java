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
        new ScreenshotTaker(view)
                .setWidthDp(ScreenshotTaker.DEFAULT_SCREENSHOT_WIDTH_DP)
                .setHeightDp(ScreenshotTaker.DEFAULT_SCREENSHOT_HEIGHT_DP)
                .setWidthPx(ScreenshotTaker.DEFAULT_SCREENSHOT_WIDTH_PX)
                .setHeightPx(ScreenshotTaker.DEFAULT_SCREENSHOT_HEIGHT_PX)
                .setDelay(ScreenshotTaker.DEFAULT_DELAY_MS)
                .layout()
                .record();
    }

    protected Context getContext() {
        return context;
    }

    public static class ScreenshotTaker {
        public static final int DEFAULT_DELAY_MS = 200;
        public static final int DEFAULT_SCREENSHOT_WIDTH_DP = 360;
        public static final int DEFAULT_SCREENSHOT_HEIGHT_DP = 640;
        public static final int DEFAULT_SCREENSHOT_WIDTH_PX = 1080;
        public static final int DEFAULT_SCREENSHOT_HEIGHT_PX = 1920;

        private final ViewHelpers helpers;
        private final View view;
        @Nullable
        private Integer delayMilliseconds = null;
        private boolean layoutDone = false;

        public ScreenshotTaker(@NonNull View view) {
            this.helpers = ViewHelpers.setupView(view);
            this.view = view;
        }

        public ScreenshotTaker setWidthDp(int widthDp) {
            helpers.setExactWidthDp(widthDp);
            return this;
        }

        public ScreenshotTaker setHeightDp(int heightDp) {
            helpers.setExactHeightDp(heightDp);
            return this;
        }

        public ScreenshotTaker setWidthPx(int widthPx) {
            helpers.setExactWidthPx(widthPx);
            return this;
        }

        public ScreenshotTaker setHeightPx(int heightPx) {
            helpers.setExactHeightPx(heightPx);
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
            layoutDone = true;
            return this;
        }

        public void record() {
            if (!layoutDone) {
                throw new IllegalStateException("Call layout() before calling record()");
            }
            Screenshot.snap(view).record();
        }
    }
}
