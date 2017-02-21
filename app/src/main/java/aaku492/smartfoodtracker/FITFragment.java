package aaku492.smartfoodtracker;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.util.Log;

import aaku492.smartfoodtracker.common.DataProvider;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-30.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class FITFragment extends Fragment {

    private static final String LOG_TAG = FITFragment.class.getName();

    @CallSuper
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof FragmentContainerActivity)) {
            throw new IllegalStateException("The container activity should be " + FragmentContainerActivity.class.getName());
        }
    }

    protected String getCurrentDeviceId() {
        return getContainerActivity().getApp().getCurrentDeviceId();
    }

    protected String getUserId() {
        return getContainerActivity().getApp().getUserId();
    }

    protected DataProvider getDataProvider() {
        return getContainerActivity().getApp().getDataProvider();
    }

    protected FragmentContainerActivity getContainerActivity() {
        return (FragmentContainerActivity) getActivity();
    }

    protected void pushFragmentActivityForResult(FragmentInitInfo fragmentInitInfo) {
        getContainerActivity().pushFragmentActivityForResult(fragmentInitInfo);
    }

    protected void pushFragmentActivity(FragmentInitInfo fragmentInitInfo) {
        getContainerActivity().pushFragmentActivity(fragmentInitInfo);
    }

    protected void popFragmentActivity() {
        getContainerActivity().popFragmentActivity();
    }

    protected void popFragmentActivityWithResult(int resultCode) {
        getContainerActivity().popFragmentActivityWithResult(resultCode);
    }

    public boolean handleStatusResult(int resultCode) {
        Log.w(LOG_TAG, "Unhandled status result code: " + resultCode);
        return false;
    }

    public boolean onBackPressed() {
        Log.w(LOG_TAG, "Unhandled onBackPressed.");
        return false;
    }

    public boolean onAcceptPressed() {
        Log.w(LOG_TAG, "Unhandled onAcceptPressed.");
        return false;
    }

    public void refresh() {
        Log.w(LOG_TAG, "Unhandled refresh.");
    }
}
