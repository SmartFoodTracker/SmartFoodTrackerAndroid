package aaku492.smartfoodtracker.inventory;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import aaku492.smartfoodtracker.R;
import aaku492.smartfoodtracker.common.ViewUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-28.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class InventoryFragmentView extends RelativeLayout {

    @BindView(R.id.inventory_list)
    protected RecyclerView inventoryList;

    @BindView(R.id.swipe_container)
    protected SwipeRefreshLayout swipeContainer;

    @BindView(R.id.add_item)
    protected FloatingActionButton addButton;

    @BindView(R.id.coordinator_layout)
    protected View rootView;

    public static InventoryFragmentView inflate(LayoutInflater inflater, ViewGroup container, Delegate delegate) {
        InventoryFragmentView view = ViewUtils.inflate(R.layout.fragment_inventory, inflater, container);
        view.setDelegate(delegate);
        return view;
    }

    private void setDelegate(final Delegate delegate) {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                delegate.onRefresh();
            }
        });

        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.addItem();
            }
        });
    }

    // Mandatory constructor
    public InventoryFragmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void render(RecyclerView.Adapter<? extends InventoryItemView.InventoryItemViewHolder> adapter) {
        inventoryList.setAdapter(adapter);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        inventoryList.setLayoutManager(layoutManager);

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_dark);

        ColorStateList rippleColor = ContextCompat.getColorStateList(getContext(), R.color.fab_ripple_color);
        addButton.setBackgroundTintList(rippleColor);
    }

    public void setRefreshing(boolean isRefreshing) {
        swipeContainer.setRefreshing(isRefreshing);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return swipeContainer.isRefreshing();
    }

    public void showMessage(String message) {
        ViewUtils.showMessage(message, rootView);
    }

    public interface Delegate {
        void onRefresh();
        void addItem();
    }
}
