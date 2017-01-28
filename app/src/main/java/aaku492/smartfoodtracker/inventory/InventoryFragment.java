package aaku492.smartfoodtracker.inventory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-28.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class InventoryFragment extends Fragment implements InventoryFragmentView.Delegate {

    @Override
    public InventoryFragmentView onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return InventoryFragmentView.inflate(inflater, container, this);
    }

    @Override
    public RecyclerView.Adapter<? extends InventoryItemView.InventoryItemViewHolder> createAdapter() {
        List<InventoryItem> mockInventory = new ArrayList<>();
        mockInventory.add(new InventoryItem("Apples"));
        mockInventory.add(new InventoryItem("Yogurt"));
        return new InventoryAdapter(mockInventory);
    }
}
