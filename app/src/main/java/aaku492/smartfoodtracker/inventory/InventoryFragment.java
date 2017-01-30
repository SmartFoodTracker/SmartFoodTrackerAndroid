package aaku492.smartfoodtracker.inventory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

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
public class InventoryFragment extends Fragment implements InventoryAdapter.Delegate {
    private static final String LOG_TAG = InventoryFragment.class.getName();

    private ArrayList<InventoryItem> inventory;
    private InventoryAdapter inventoryAdapter;

    @Override
    public InventoryFragmentView onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inventory = new ArrayList<>();
        inventoryAdapter = new InventoryAdapter(inventory, this);

        getMainActivity().setTitle(getString(R.string.inventory_fragment_title));

        InventoryFragmentView view = InventoryFragmentView.inflate(inflater, container, inventoryAdapter);
        fetchInventory();
        return view;
    }

    private void fetchInventory() {
        getMainActivity().getApp().getDataProvider().getInventory(getMainActivity().getApp().getCurrentDeviceId())
                .enqueue(new Callback<List<InventoryItem>>() {
                    @Override
                    public void onResponse(Call<List<InventoryItem>> call, final Response<List<InventoryItem>> response) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                inventory.clear();
                                inventory.addAll(response.body());
                                inventoryAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<List<InventoryItem>> call, Throwable t) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e(LOG_TAG, getString(R.string.inventory_fetch_error));
                                ViewUtils.showMessage(getString(R.string.inventory_fetch_error), getView());
                            }
                        });
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

        int index = inventory.indexOf(item);
        if (index < 0) {
            Log.e(LOG_TAG, "Checked inventory item not found in the backing model list.");
            ViewUtils.showMessage(getString(R.string.generic_error), getView());
            return;
        }
        inventory.remove(index);
        inventoryAdapter.notifyItemRemoved(index);
    }

    private MainActivity getMainActivity() {
        if (super.getActivity() instanceof MainActivity) {
            return (MainActivity) super.getActivity();
        }

        throw new IllegalStateException("The container activity should be " + MainActivity.class.getName());
    }
}
