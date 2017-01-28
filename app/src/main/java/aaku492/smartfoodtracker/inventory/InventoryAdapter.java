package aaku492.smartfoodtracker.inventory;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-28.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
class InventoryAdapter extends RecyclerView.Adapter<InventoryItemView.InventoryItemViewHolder> {

    private final List<InventoryItem> inventoryItems;

    public InventoryAdapter(List<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    @Override
    public InventoryItemView.InventoryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InventoryItemView.InventoryItemViewHolder(
                InventoryItemView.inflate(LayoutInflater.from(parent.getContext()), parent));
    }

    @Override
    public void onBindViewHolder(InventoryItemView.InventoryItemViewHolder holder, int position) {
        ((InventoryItemView) holder.itemView).render(inventoryItems.get(position));
    }

    @Override
    public int getItemCount() {
        return inventoryItems.size();
    }
}