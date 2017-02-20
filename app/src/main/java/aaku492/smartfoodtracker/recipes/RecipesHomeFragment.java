package aaku492.smartfoodtracker.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import aaku492.smartfoodtracker.FITFragment;
import aaku492.smartfoodtracker.FragmentInitInfo;
import aaku492.smartfoodtracker.R;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-02-19.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipesHomeFragment extends FITFragment {

    public static FragmentInitInfo getFragmentInitInfo() {
        return new FragmentInitInfo(false, RecipesHomeFragment.class);
    }

    @Override
    public RecipesHomeFragmentView onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getContainerActivity().setTitle(R.string.recipes_fragment_title);
        return RecipesHomeFragmentView.inflate(inflater, container);
    }
}
