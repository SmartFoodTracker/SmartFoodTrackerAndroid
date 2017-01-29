package aaku492.smartfoodtracker.inventory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import aaku492.smartfoodtracker.MainActivity;
import aaku492.smartfoodtracker.R;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-28.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class InventoryFragment extends Fragment implements InventoryAdapter.Delegate {
    private static final String LOG_TAG = InventoryFragment.class.getName();

    private ArrayList<InventoryItem> mockInventory;
    private InventoryAdapter inventoryAdapter;

    @Override
    public InventoryFragmentView onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: replace with actual data
        mockInventory = new ArrayList<>();
        mockInventory.add(new InventoryItem("Apples", "0"));
        mockInventory.add(new InventoryItem("Yogurt", "1"));

        inventoryAdapter = new InventoryAdapter(mockInventory, this);

        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setTitle(getString(R.string.inventory_fragment_title));
        } else {
            Log.e(LOG_TAG, "The container activity is not MainActivity. Don't know how to set the title.");
        }

        return InventoryFragmentView.inflate(inflater, container, inventoryAdapter);
    }

    @Override
    public void onCheckedChanged(InventoryItem item, boolean isChecked) {
        if (!isChecked) {
            Log.e(LOG_TAG, "Un-checking shouldn't be allowed right now. Wtf happened?!");
            return;
        }

        int index = mockInventory.indexOf(item);
        if (index < 0) {
            Log.e(LOG_TAG, "Checked inventory item not found in the backing model list.");
            return;
        }
        mockInventory.remove(index);
        inventoryAdapter.notifyItemRemoved(index);
    }
}
