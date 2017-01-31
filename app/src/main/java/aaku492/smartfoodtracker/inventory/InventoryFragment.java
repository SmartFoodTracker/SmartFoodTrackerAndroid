package aaku492.smartfoodtracker.inventory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import aaku492.smartfoodtracker.FragmentContainerActivity;
import aaku492.smartfoodtracker.FragmentInitInfo;
import aaku492.smartfoodtracker.R;
import aaku492.smartfoodtracker.common.DataProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-28.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class InventoryFragment extends Fragment implements InventoryAdapter.Delegate, InventoryFragmentView.Delegate {
    private static final String LOG_TAG = InventoryFragment.class.getName();

    private InventoryAdapter inventoryAdapter;

    public static FragmentInitInfo getFragmentInitInfo() {
        return new FragmentInitInfo(InventoryFragment.class);
    }

    @Override
    public InventoryFragmentView onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inventoryAdapter = new InventoryAdapter(new ArrayList<InventoryItem>(), this);

        getContainerActivity().setTitle(getString(R.string.inventory_fragment_title));

        InventoryFragmentView view = InventoryFragmentView.inflate(inflater, container, this);
        view.render(inventoryAdapter);
        fetchInventory(view);
        return view;
    }

    private void fetchInventory(final InventoryFragmentView view) {
        view.setRefreshing(true);
        getDataProvider().getInventory(getCurrentDeviceId())
                .enqueue(new Callback<List<InventoryItem>>() {
                    @Override
                    public void onResponse(Call<List<InventoryItem>> call, final Response<List<InventoryItem>> response) {
                        if (response.isSuccessful()) {
                            inventoryAdapter.clear();
                            inventoryAdapter.addAll(response.body());
                        } else {
                            onFailure("Got back error response: " + response.code());
                        }
                        view.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<List<InventoryItem>> call, Throwable t) {
                        onFailure("Exception:\n" + t.toString());
                        view.setRefreshing(false);
                    }

                    private void onFailure(String logReason) {
                        Log.e(LOG_TAG, getString(R.string.inventory_fetch_error) + " " + logReason);
                        //noinspection ConstantConditions
                        getView().showMessage(getString(R.string.inventory_fetch_error));
                    }
                });
    }

    private String getCurrentDeviceId() {
        return getContainerActivity().getApp().getCurrentDeviceId();
    }

    private DataProvider getDataProvider() {
        return getContainerActivity().getApp().getDataProvider();
    }

    @SuppressWarnings("ConstantConditions")
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
                    .enqueue(new Callback<List<InventoryItem>>() {
                        @Override
                        public void onResponse(Call<List<InventoryItem>> call, Response<List<InventoryItem>> response) {
                            if (response.isSuccessful()) {
                                inventoryAdapter.clear();
                                inventoryAdapter.addAll(response.body());
                                getView().showMessage(getString(R.string.item_consumed_message_formatter, item.getTitle()));
                            } else {
                                onFailure("Got back error response: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<InventoryItem>> call, Throwable t) {
                            onFailure("Exception:\n" + t.toString());
                        }

                        private void onFailure(String logReason) {
                            inventoryAdapter.add(item);
                            Log.e(LOG_TAG, getString(R.string.item_delete_failure) + " " + logReason);
                            getView().showMessage(getString(R.string.item_delete_failure));
                        }
                    });
        } catch (NoSuchElementException e) {
            Log.e(LOG_TAG, e.toString());
            getView().showMessage(getString(R.string.generic_error));
        }
    }

    private FragmentContainerActivity getContainerActivity() {
        if (super.getActivity() instanceof FragmentContainerActivity) {
            return (FragmentContainerActivity) super.getActivity();
        }

        throw new IllegalStateException("The container activity should be " + FragmentContainerActivity.class.getName());
    }

    @Override
    public void onRefresh() {
        fetchInventory(getView());
    }

    @Override
    public void addItem() {
        //noinspection ConstantConditions
        getView().showMessage("Coming soon!");
    }

    @Override
    public InventoryFragmentView getView() {
        return (InventoryFragmentView) super.getView();
    }
}
