package aaku492.smartfoodtracker.inventory;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import aaku492.smartfoodtracker.R;
import aaku492.smartfoodtracker.ViewUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-28.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class InventoryFragmentView extends LinearLayout {

    @BindView(R.id.inventory_list)
    protected RecyclerView inventoryList;

    public static InventoryFragmentView inflate(LayoutInflater inflater,
                                                ViewGroup container,
                                                RecyclerView.Adapter<? extends InventoryItemView.InventoryItemViewHolder> adapter) {
        InventoryFragmentView view = ViewUtils.inflate(R.layout.fragment_inventory, inflater, container);
        view.inventoryList.setAdapter(adapter);
        return view;
    }

    // Mandatory constructor
    public InventoryFragmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        inventoryList.setLayoutManager(layoutManager);
    }
}
