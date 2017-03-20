package aaku492.smartfoodtracker.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import aaku492.smartfoodtracker.FITFragment;
import aaku492.smartfoodtracker.FragmentInitInfo;
import aaku492.smartfoodtracker.R;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-19.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class AboutFragment extends FITFragment {
    @Override
    protected void onRefresh() {
    }

    public static FragmentInitInfo getFragmentInfo() {
        return new FragmentInitInfo(AboutFragment.class)
                .setIsDetailsScreen(false)
                .setIsModal(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getContainerActivity().setTitle(R.string.about_fragment_title);
        return AboutFragmentView.inflate(inflater, container);
    }
}
