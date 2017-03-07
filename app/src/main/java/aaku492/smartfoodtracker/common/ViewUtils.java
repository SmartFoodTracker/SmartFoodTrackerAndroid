package aaku492.smartfoodtracker.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;

import aaku492.smartfoodtracker.R;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-28.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class ViewUtils {

    public static <T extends View> T inflate(@LayoutRes int layoutId, LayoutInflater inflater, ViewGroup container) {
        //noinspection unchecked
        return (T) inflater.inflate(layoutId, container, false);
    }

    public static void showMessage(String message, View container) {
        Snackbar.make(container, message, Snackbar.LENGTH_SHORT).show();
    }

    public static ProgressDialog createAndShowProgressDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.show();
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        //noinspection ConstantConditions
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progress_dialog);
        return dialog;
    }

    public static void closeKeyboard(AppCompatActivity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private static class Bouncer {
        private static final long DURATION = 50;
        private final View view;
        private final float targetScale;

        private Bouncer(View view, float targetScale) {
            this.view = view;
            this.targetScale = targetScale;
        }

        ViewPropertyAnimator run() {
            return view.animate()
                    .scaleX(targetScale)
                    .scaleY(targetScale)
                    .setDuration(DURATION);
        }
    }

    public static void bounceAnimation(final View view) {
        final float maxScale = 1.2f;
        final int numSteps = 3;

        final ArrayList<Bouncer> bouncers = new ArrayList<>(numSteps + 1);

        for (int i = 0; i < numSteps; ++i) {
            float targetScaleDelta = ((maxScale - 1.0f) * (numSteps - i))/numSteps;
            if (i % 2 == 1) {
                targetScaleDelta *= -1;
            }
            bouncers.add(new Bouncer(view, targetScaleDelta + 1.0f));
        }

        bouncers.add(new Bouncer(view, 1.0f));

        final ViewPropertyAnimator[] animator = {bouncers.get(0).run()};

        for (int i = 1; i < bouncers.size(); ++i) {
            final int finalI = i;
            animator[0].withEndAction(new Runnable() {
                @Override
                public void run() {
                    animator[0] = bouncers.get(finalI).run();
                }
            });
        }
    }
}
