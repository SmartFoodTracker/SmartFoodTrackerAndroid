package aaku492.smartfoodtracker.common;

import android.support.annotation.LayoutRes;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        Snackbar.make(container, message, BaseTransientBottomBar.LENGTH_LONG).show();
    }
}
