package aaku492.smartfoodtracker.recipes;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import aaku492.smartfoodtracker.R;
import aaku492.smartfoodtracker.common.ViewUtils;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-02-20.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipesHomeFragmentView extends LinearLayout {
    public RecipesHomeFragmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static RecipesHomeFragmentView inflate(LayoutInflater inflater, ViewGroup container) {
        return ViewUtils.inflate(R.layout.fragment_recipes_home, inflater, container);
    }
}
