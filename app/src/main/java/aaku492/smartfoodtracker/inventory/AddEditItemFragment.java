package aaku492.smartfoodtracker.inventory;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import aaku492.smartfoodtracker.FragmentContainerActivity;
import aaku492.smartfoodtracker.FragmentInitInfo;
import aaku492.smartfoodtracker.R;
import aaku492.smartfoodtracker.SFTFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-30.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class AddEditItemFragment extends SFTFragment {
    private static final String ITEM_ID = "item_id";
    private static final String ITEM = "item";
    private static final String LOG_TAG = AddEditItemFragment.class.getName();

    private InventoryItem item;

    public static FragmentInitInfo getFragmentInitInfo(@Nullable String itemId) {
        Bundle args = new Bundle();
        args.putString(ITEM_ID, itemId);
        return new FragmentInitInfo(true, AddEditItemFragment.class, args);
    }

    @Override
    public AddEditItemFragmentView onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final AddEditItemFragmentView view = AddEditItemFragmentView.inflate(inflater, container);

        if (savedInstanceState != null && savedInstanceState.getSerializable(ITEM) != null) {
            item = (InventoryItem) savedInstanceState.getSerializable(ITEM);
            //noinspection ConstantConditions
            view.render(item);
            getContainerActivity().setTitle(getString(R.string.edit_item));
        } else if (getArguments() != null && getArguments().getString(ITEM_ID) != null) {
            fetchItemAndRender(getArguments().getString(ITEM_ID), view);
            getContainerActivity().setTitle(getString(R.string.edit_item));
        } else {
            item = new InventoryItem(null, null, null, null, null, null);
            view.render(item);
            getContainerActivity().setTitle(getString(R.string.add_item));
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putSerializable(ITEM, item);
    }

    private void fetchItemAndRender(String itemId, final AddEditItemFragmentView view) {
        view.setLoading(true);
        getDataProvider().getItem(getCurrentDeviceId(), itemId)
                .enqueue(new Callback<InventoryItem>() {
                    @Override
                    public void onResponse(Call<InventoryItem> call, Response<InventoryItem> response) {
                        if (response.isSuccessful()) {
                            view.render(response.body());
                            item = response.body();
                        } else {
                            onFailure("Got back error response: " + response.code());
                        }
                        view.setLoading(false);
                    }

                    @Override
                    public void onFailure(Call<InventoryItem> call, Throwable t) {
                        onFailure("Exception:\n" + t.toString());
                    }

                    private void onFailure(String logMessage) {
                        Log.e(LOG_TAG, getString(R.string.item_fetch_error) + " " + logMessage);
                        getContainerActivity().popFragment(FragmentContainerActivity.RESULT_ERROR);
                    }
                });
    }

    @Override
    public boolean onBackPressed() {
        getContainerActivity().popFragment(Activity.RESULT_CANCELED);
        return true;
    }

    @Override
    public boolean onAcceptPressed() {
        getContainerActivity().popFragment(Activity.RESULT_OK);
        return true;
    }
}
