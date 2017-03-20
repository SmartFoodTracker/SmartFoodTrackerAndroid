package aaku492.smartfoodtracker.inventory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import aaku492.smartfoodtracker.FITFragment;
import aaku492.smartfoodtracker.FragmentContainerActivity;
import aaku492.smartfoodtracker.FragmentInitInfo;
import aaku492.smartfoodtracker.R;
import retrofit2.Response;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-28.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class InventoryFragment extends FITFragment implements InventoryAdapter.Delegate, InventoryFragmentView.Delegate {
    private static final String LOG_TAG = InventoryFragment.class.getName();
    private static final String IS_EDITING = "is_editing";
    private InventoryAdapter inventoryAdapter;

    private Boolean isEditing = null;

    public static FragmentInitInfo getFragmentInitInfo() {
        return new FragmentInitInfo(InventoryFragment.class)
                .setIsModal(false)
                .setIsDetailsScreen(false);
    }

    @Override
    public InventoryFragmentView onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inventoryAdapter = new InventoryAdapter(new ArrayList<InventoryItem>(), this);

        getContainerActivity().setTitle(R.string.inventory_fragment_title);

        InventoryFragmentView view = InventoryFragmentView.inflate(inflater, container, this);
        view.render(inventoryAdapter);

        isEditing = savedInstanceState == null ? null : (Boolean) savedInstanceState.getSerializable(IS_EDITING);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(IS_EDITING, isEditing);
    }

    private void fetchInventoryAndRender() {
        final InventoryFragmentView view = getView();
        view.setRefreshing(true);
        getDataProvider().getInventory(getCurrentDeviceId())
                .enqueue(new FITRequestCallback<List<InventoryItem>>() {
                    @Override
                    protected void onFailure(String errorDescription) {
                        view.setRefreshing(false);
                        Log.e(LOG_TAG, getString(R.string.inventory_fetch_error) + " " + errorDescription);
                        //noinspection ConstantConditions
                        getView().showMessage(getString(R.string.inventory_fetch_error));
                    }

                    @Override
                    protected void onSuccessfulResponse(Response<List<InventoryItem>> response) {
                        view.setRefreshing(false);
                        inventoryAdapter.clear();
                        inventoryAdapter.addAll(response.body());
                    }
                });
    }

    @Override
    public void onCheckedChanged(final InventoryItem item, boolean isChecked) {
        if (!isChecked) {
            Log.e(LOG_TAG, "Un-checking shouldn't be allowed right now. Wtf happened?!");
            getView().showMessage(getString(R.string.generic_error));
            return;
        }

        try {
            // Remove from the UI, but add it back if the request fails
            inventoryAdapter.remove(item);

            getDataProvider().deleteItem(getCurrentDeviceId(), item.getId())
                    .enqueue(new FITRequestCallback<List<InventoryItem>>() {
                        @Override
                        protected void onFailure(String errorDescription) {
                            inventoryAdapter.add(item);
                            Log.e(LOG_TAG, getString(R.string.item_delete_failure) + " " + errorDescription);
                            getView().showMessage(getString(R.string.item_delete_failure));
                        }

                        @Override
                        protected void onSuccessfulResponse(Response<List<InventoryItem>> response) {
                            inventoryAdapter.clear();
                            inventoryAdapter.addAll(response.body());
                            getView().showMessage(getString(R.string.item_consumed_message_formatter, item.getTitle()));
                        }
                    });
        } catch (NoSuchElementException e) {
            Log.e(LOG_TAG, e.toString());
            getView().showMessage(getString(R.string.generic_error));
        }
    }

    @Override
    public void onItemSelected(InventoryItem item) {
        if (isEditing != null) {
            throw new IllegalStateException("isEditing value should've been consumed.");
        }
        isEditing = true;
        pushFragmentActivityForResult(AddEditItemFragment.getFragmentInitInfo(item.getId()));
    }

    @Override
    public void onRefresh() {
        fetchInventoryAndRender();
    }

    @Override
    public void addItem() {
        if (isEditing != null) {
            throw new IllegalStateException("isEditing value should've been consumed.");
        }
        isEditing = false;
        //noinspection ConstantConditions
        pushFragmentActivityForResult(AddEditItemFragment.getFragmentInitInfo(null));
    }

    @NonNull
    @Override
    public InventoryFragmentView getView() {
        if (super.getView() == null) {
            throw new IllegalStateException("The view was not available when requested: " + InventoryFragment.class.getName());
        }
        return (InventoryFragmentView) super.getView();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean handleStatusResult(int resultCode) {
        switch (resultCode) {
            case FragmentContainerActivity.RESULT_OK:
                getView().showMessage(getString(isEditing ? R.string.item_edited : R.string.item_added));
                isEditing = null;
                return true;
            case FragmentContainerActivity.RESULT_CANCELED:
                getView().showMessage(getString(isEditing ? R.string.item_edit_cancelled : R.string.item_add_cancelled));
                isEditing = null;
                return true;
            case FragmentContainerActivity.RESULT_ERROR:
                if (!isEditing) {
                    throw new IllegalStateException("RESULT_ERROR not expected when creating new item.");
                }
                getView().showMessage(getString(R.string.item_fetch_error));
                isEditing = null;
                return true;
            default:
                return super.handleStatusResult(resultCode);
        }
    }

    @Override
    public void refresh() {
        getView().scrollToTop();
        onRefresh();
    }
}
