package aaku492.smartfoodtracker.inventory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import aaku492.smartfoodtracker.MainActivity;
import aaku492.smartfoodtracker.R;
import aaku492.smartfoodtracker.common.ViewUtils;
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

    @Override
    public InventoryFragmentView onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inventoryAdapter = new InventoryAdapter(new ArrayList<InventoryItem>(), this);

        getMainActivity().setTitle(getString(R.string.inventory_fragment_title));

        InventoryFragmentView view = InventoryFragmentView.inflate(inflater, container, inventoryAdapter, this);
        fetchInventory(view);
        return view;
    }

    private void fetchInventory(final InventoryFragmentView view) {
        view.setRefreshing(true);
        getMainActivity().getApp().getDataProvider().getInventory(getMainActivity().getApp().getCurrentDeviceId())
                .enqueue(new Callback<List<InventoryItem>>() {
                    @Override
                    public void onResponse(Call<List<InventoryItem>> call, final Response<List<InventoryItem>> response) {
                        inventoryAdapter.clear();
                        inventoryAdapter.addAll(response.body());
                        view.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<List<InventoryItem>> call, Throwable t) {
                        Log.e(LOG_TAG, getString(R.string.inventory_fetch_error));
                        ViewUtils.showMessage(getString(R.string.inventory_fetch_error), view);
                        view.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onCheckedChanged(InventoryItem item, boolean isChecked) {
        if (!isChecked) {
            Log.e(LOG_TAG, "Un-checking shouldn't be allowed right now. Wtf happened?!");
            ViewUtils.showMessage(getString(R.string.generic_error), getView());
            return;
        }

        try {
            inventoryAdapter.remove(item);
        } catch (NoSuchElementException e) {
            Log.e(LOG_TAG, e.toString());
            ViewUtils.showMessage(getString(R.string.generic_error), getView());
        }
    }

    private MainActivity getMainActivity() {
        if (super.getActivity() instanceof MainActivity) {
            return (MainActivity) super.getActivity();
        }

        throw new IllegalStateException("The container activity should be " + MainActivity.class.getName());
    }

    @Override
    public void onRefresh() {
        fetchInventory((InventoryFragmentView) getView());
    }
}
