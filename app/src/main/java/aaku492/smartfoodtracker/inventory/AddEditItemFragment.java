package aaku492.smartfoodtracker.inventory;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import aaku492.smartfoodtracker.FITFragment;
import aaku492.smartfoodtracker.FragmentContainerActivity;
import aaku492.smartfoodtracker.FragmentInitInfo;
import aaku492.smartfoodtracker.R;
import aaku492.smartfoodtracker.common.FunctionalUtils;
import aaku492.smartfoodtracker.common.ViewUtils;
import retrofit2.Callback;
import retrofit2.Response;

import static aaku492.smartfoodtracker.common.FunctionalUtils.any;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-30.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class AddEditItemFragment extends FITFragment implements AddEditItemFragmentView.Delegate {
    private static final String ITEM_ID = "item_id";
    private static final String ITEM = "item";
    private static final String OLD_QUANTITY = "old_quantity";
    private static final String LOG_TAG = AddEditItemFragment.class.getName();

    private InventoryItem item;
    // Only used when editing an item
    private double oldQuantity = -1;

    public static FragmentInitInfo getFragmentInitInfo(@Nullable String itemId) {
        Bundle args = new Bundle();
        args.putString(ITEM_ID, itemId);
        return new FragmentInitInfo(AddEditItemFragment.class)
                .setIsModal(true)
                .setIsDetailsScreen(false)
                .setArgs(args);
    }

    @Override
    public AddEditItemFragmentView onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final AddEditItemFragmentView view = AddEditItemFragmentView.inflate(inflater, container, this);

        if (savedInstanceState != null && savedInstanceState.getSerializable(ITEM) != null) {
            // Left the screen and coming back. Could be either creating or editing, so check the item.id
            item = (InventoryItem) savedInstanceState.getSerializable(ITEM);
            oldQuantity = savedInstanceState.getDouble(OLD_QUANTITY, -1);
            //noinspection ConstantConditions
            view.render(item);
            getContainerActivity().setTitle(getString(isEditingItem() ? R.string.edit_item : R.string.add_item));
        } else if (getArguments() != null && getArguments().getString(ITEM_ID) != null) {
            // Editing an existing item
            getContainerActivity().setTitle(getString(R.string.edit_item));
            item = null;
            // item = null marks the item fetching as pending. This will be treated as the "edit item"
            // case in the initial stages of fragment loading.
            // Don't fetch item/render yet. That'll happen when super class calls onRefresh
        } else {
            // Creating new item
            item = new InventoryItem(null, null, 0.0, InventoryItem.Unit.values()[0], null, null);
            // It should not be used
            oldQuantity = -1;
            view.render(item);
            getContainerActivity().setTitle(getString(R.string.add_item));
        }

        return view;
    }

    @Override
    protected void onRefresh() {
        if (isEditingItem()) {
            fetchItemAndRender(getArguments().getString(ITEM_ID));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(ITEM, item);
        bundle.putDouble(OLD_QUANTITY, oldQuantity);
    }

    @SuppressWarnings("ConstantConditions")
    private void fetchItemAndRender(String itemId) {
        final AddEditItemFragmentView view = (AddEditItemFragmentView) getView();
        view.setLoading(true);
        getDataProvider().getItem(getCurrentDeviceId(), itemId)
                .enqueue(new FITRequestCallback<InventoryItem>() {
                    @Override
                    protected void onFailure(String errorDescription) {
                        view.setLoading(false);
                        Log.e(LOG_TAG, getString(R.string.item_fetch_error) + " " + errorDescription);
                        popFragmentActivityWithResult(FragmentContainerActivity.RESULT_ERROR);
                    }

                    @Override
                    protected void onSuccessfulResponse(Response<InventoryItem> response) {
                        view.render(response.body());
                        item = response.body();
                        oldQuantity = item.getQuantity();
                        view.setLoading(false);
                    }
                });
    }

    @Override
    public boolean onBackPressed() {
        popFragmentActivityWithResult(Activity.RESULT_CANCELED);
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

        final Callback<List<InventoryItem>> mutationCallback = new FITRequestCallback<List<InventoryItem>>() {
            @Override
            protected void onFailure(String errorDescription) {
                ((AddEditItemFragmentView)getView()).setLoading(false);
                if (isEditingItem()) {
                    ((AddEditItemFragmentView)getView()).showMessage(getString(R.string.item_edit_error));
                    Log.e(LOG_TAG, getString(R.string.item_edit_error) + " " + errorDescription);
                } else {
                    ((AddEditItemFragmentView)getView()).showMessage(getString(R.string.item_add_error));
                    Log.e(LOG_TAG, getString(R.string.item_add_error) + " " + errorDescription);
                }
            }

            @Override
            protected void onSuccessfulResponse(Response<List<InventoryItem>> response) {
                ((AddEditItemFragmentView)getView()).setLoading(false);
                popFragmentActivityWithResult(FragmentContainerActivity.RESULT_OK);
            }
        };

        getDataProvider().getInventory(getCurrentDeviceId()).enqueue(new FITRequestCallback<List<InventoryItem>>() {
            @Override
            protected void onFailure(String errorDescription) {
                ((AddEditItemFragmentView)getView()).setLoading(false);
                ((AddEditItemFragmentView)getView()).showMessage(getString(R.string.inventory_fetch_error));
                Log.e(LOG_TAG, getString(R.string.inventory_fetch_error));
            }

            @Override
            protected void onSuccessfulResponse(Response<List<InventoryItem>> response) {
                boolean duplicate = any(response.body(), new FunctionalUtils.Predicate<InventoryItem>() {
                    @Override
                    public boolean test(InventoryItem in) {
                        // Don't mark as duplicate if the two items have the same IDs, because you need to allow editing in the case.
                        // (This doesn't affect creation, because this.item.getId() will return null, and equality
                        // will always be false). This will catch item creation with duplicate titles, or item editing
                        // such that the new title is duplicate to another item.
                        return !in.getId().equals(AddEditItemFragment.this.item.getId()) &&
                                in.getTitle().toLowerCase().equals(AddEditItemFragment.this.item.getTitle().toLowerCase());
                    }
                });

                if (duplicate) {
                    ((AddEditItemFragmentView)getView()).setLoading(false);
                    ((AddEditItemFragmentView)getView()).showMessage(getString(R.string.duplicate_item_error_formatter, item.getTitle()));
                } else {
                    if (isEditingItem()) {
                        if (oldQuantity < 0) {
                            throw new IllegalStateException("Sanity check failed; oldQuantity should've been set");
                        }
                        item.setQuantity(item.getQuantity() - oldQuantity);
                        getDataProvider().editItem(getCurrentDeviceId(), item.getId(), item)
                                .enqueue(mutationCallback);
                    } else {
                        getDataProvider().addItem(getCurrentDeviceId(), item)
                                .enqueue(mutationCallback);
                    }
                }
            }
        });

        return true;
    }

    private boolean isEditingItem() {
        // item can be null if the fetching is not completed
        return item == null || item.getId() != null;
    }

    @Override
    public void onFormCompleted() {
        // Only send the results when the modal accept button is pressed
        ViewUtils.closeKeyboard(getContainerActivity());
    }
}
