package aaku492.smartfoodtracker.inventory;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import aaku492.smartfoodtracker.R;
import aaku492.smartfoodtracker.ViewUtils;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-28.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class InventoryFragmentView extends LinearLayout {
    public static InventoryFragmentView inflate(LayoutInflater inflater, ViewGroup container) {
        return ViewUtils.inflate(R.layout.fragment_inventory, inflater, container);
    }

    // Mandatory constructor
    public InventoryFragmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
