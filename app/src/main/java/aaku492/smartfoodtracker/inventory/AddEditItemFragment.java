package aaku492.smartfoodtracker.inventory;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import aaku492.smartfoodtracker.FragmentContainerActivity;
import aaku492.smartfoodtracker.FragmentInitInfo;
import aaku492.smartfoodtracker.R;
import aaku492.smartfoodtracker.FITFragment;
import aaku492.smartfoodtracker.common.ViewUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-30.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class AddEditItemFragment extends FITFragment implements AddEditItemFragmentView.Delegate {
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
        final AddEditItemFragmentView view = AddEditItemFragmentView.inflate(inflater, container, this);

        if (savedInstanceState != null && savedInstanceState.getSerializable(ITEM) != null) {
            // Left the screen and coming back. Could be either creating or editing, so check the item.id
            item = (InventoryItem) savedInstanceState.getSerializable(ITEM);
            //noinspection ConstantConditions
            view.render(item);
            getContainerActivity().setTitle(getString(isEditingItem() ? R.string.edit_item : R.string.add_item));
        } else if (getArguments() != null && getArguments().getString(ITEM_ID) != null) {
            // Editing an existing item
            fetchItemAndRender(getArguments().getString(ITEM_ID), view);
            getContainerActivity().setTitle(getString(R.string.edit_item));
        } else {
            // Creating new item
            item = new InventoryItem(null, null, 0.0, InventoryItem.Unit.values()[0], null, null);
            view.render(item);
            getContainerActivity().setTitle(getString(R.string.add_item));
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
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

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean onAcceptPressed() {
        if (!((AddEditItemFragmentView)getView()).validateInput()) {
            return true;
        }

        ViewUtils.closeKeyboard(getContainerActivity());
        ((AddEditItemFragmentView)getView()).setLoading(true);

        Callback<List<InventoryItem>> callback = new Callback<List<InventoryItem>>() {
            @Override
            public void onResponse(Call<List<InventoryItem>> call, Response<List<InventoryItem>> response) {
                ((AddEditItemFragmentView)getView()).setLoading(false);
                if (response.isSuccessful()) {
                    getContainerActivity().popFragment(FragmentContainerActivity.RESULT_OK);
                } else {
                    onFailure("Got back error response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<InventoryItem>> call, Throwable t) {
                ((AddEditItemFragmentView)getView()).setLoading(false);
                onFailure("Exception:\n" + t.toString());
            }

            private void onFailure(String logMessage) {
                if (isEditingItem()) {
                    ((AddEditItemFragmentView)getView()).showMessage(getString(R.string.item_edit_error));
                    Log.e(LOG_TAG, getString(R.string.item_edit_error) + " " + logMessage);
                } else {
                    ((AddEditItemFragmentView)getView()).showMessage(getString(R.string.item_add_error));
                    Log.e(LOG_TAG, getString(R.string.item_add_error) + " " + logMessage);
                }
            }
        };

        if (isEditingItem()) {
            getDataProvider().editItem(getCurrentDeviceId(), item.getId(), item)
                    .enqueue(callback);
        } else {
            getDataProvider().addItem(getCurrentDeviceId(), item)
                    .enqueue(callback);
        }

        return true;
    }

    private boolean isEditingItem() {
        return item.getId() != null;
    }

    @Override
    public void onFormCompleted() {
        // Only send the results when the modal accept button is pressed
        ViewUtils.closeKeyboard(getContainerActivity());
    }
}
