package aaku492.smartfoodtracker.inventory;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import aaku492.smartfoodtracker.R;
import aaku492.smartfoodtracker.common.ViewUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-28.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class InventoryItemView extends CardView {

    @BindView(R.id.item_title)
    protected TextView itemTitle;

    @BindView(R.id.item_quantity)
    protected TextView itemQuantity;

    @BindView(R.id.item_checkbox)
    protected CheckBox itemCheckbox;
    
    private InventoryItem item;
    private Delegate delegate;

    public static InventoryItemView inflate(LayoutInflater inflater, ViewGroup container, Delegate delegate) {
        InventoryItemView view = ViewUtils.inflate(R.layout.inventory_item, inflater, container);
        view.setDelegate(delegate);
        return view;
    }

    // Mandatory constructor
    public InventoryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void render(final InventoryItem item) {
        this.item = item;
        itemTitle.setText(item.getTitle());
        itemCheckbox.setOnCheckedChangeListener(null);
        itemCheckbox.setChecked(false);
        itemCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                delegate.onCheckedChanged(item, isChecked);
            }
        });

        if (item.getQuantity() == null) {
            itemQuantity.setVisibility(GONE);
        } else {
            String displayUnits = getContext().getResources().getStringArray(R.array.item_quantity_units_options)[item.getUnits().ordinal()];
            itemQuantity.setText(getContext().getString(R.string.item_quantity_formatter, item.getQuantity(), displayUnits));
            itemQuantity.setVisibility(VISIBLE);
        }
    }

    public void setDelegate(final Delegate delegate) {
        this.delegate = delegate;
        itemCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                delegate.onCheckedChanged(item, isChecked);
            }
        });

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.onItemSelected(item);
            }
        });
    }

    public static class InventoryItemViewHolder extends RecyclerView.ViewHolder {
        public InventoryItemViewHolder(InventoryItemView itemView) {
            super(itemView);
        }
    }

    public interface Delegate {
        void onCheckedChanged(InventoryItem item, boolean isChecked);
        void onItemSelected(InventoryItem item);
    }
}
