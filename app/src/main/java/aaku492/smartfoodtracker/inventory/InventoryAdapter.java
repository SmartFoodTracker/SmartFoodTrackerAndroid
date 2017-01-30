package aaku492.smartfoodtracker.inventory;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-28.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
class InventoryAdapter extends RecyclerView.Adapter<InventoryItemView.InventoryItemViewHolder> {
    private final List<InventoryItem> inventoryItems;
    private final Delegate delegate;

    public InventoryAdapter(List<InventoryItem> inventoryItems, Delegate delegate) {
        this.inventoryItems = inventoryItems;
        this.delegate = delegate;
    }

    @Override
    public InventoryItemView.InventoryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InventoryItemView.InventoryItemViewHolder(
                InventoryItemView.inflate(LayoutInflater.from(parent.getContext()), parent, delegate));
    }

    @Override
    public void onBindViewHolder(InventoryItemView.InventoryItemViewHolder holder, int position) {
        ((InventoryItemView) holder.itemView).render(inventoryItems.get(position));
    }

    @Override
    public int getItemCount() {
        return inventoryItems.size();
    }

    public void clear() {
        inventoryItems.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<InventoryItem> list) {
        inventoryItems.addAll(list);
        notifyDataSetChanged();
    }

    public void remove(InventoryItem item) {
        int index = inventoryItems.indexOf(item);
        if (index < 0) {
            throw new NoSuchElementException("Checked inventory item not found in the backing model list.");
        }

        inventoryItems.remove(index);
        notifyItemRemoved(index);
    }

    public interface Delegate extends InventoryItemView.Delegate {
    }
}