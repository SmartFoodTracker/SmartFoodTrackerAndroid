package aaku492.smartfoodtracker.inventory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import aaku492.smartfoodtracker.R;
import aaku492.smartfoodtracker.ViewUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-28.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class InventoryItemView extends LinearLayout {

    @BindView(R.id.inventory_title)
    protected TextView inventoryTitle;

    public static InventoryItemView inflate(LayoutInflater inflater, ViewGroup container) {
        return ViewUtils.inflate(R.layout.inventory_item, inflater, container);
    }

    // Mandatory constructor
    public InventoryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void render(InventoryItem item) {
        inventoryTitle.setText(item.getTitle());
    }

    public static class InventoryItemViewHolder extends RecyclerView.ViewHolder {
        public InventoryItemViewHolder(InventoryItemView itemView) {
            super(itemView);
        }
    }
}
