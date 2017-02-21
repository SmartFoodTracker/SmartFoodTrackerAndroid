package aaku492.smartfoodtracker.recipes;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-02-20.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 *
 * Adapted from: https://gist.github.com/ssinss/e06f12ef66c51252563e
 */
public abstract class RecipesHomeOnScrollListener extends RecyclerView.OnScrollListener {
    private final StaggeredGridLayoutManager layoutManager;
    private boolean loading = true;
    private int previousTotal;
    private static final int visibleThreshold = 5;

    public RecipesHomeOnScrollListener(StaggeredGridLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int[] firstVisibleItems = layoutManager.findFirstVisibleItemPositions(null);

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItems[firstVisibleItems.length - 1] + visibleThreshold)) {
            loading = true;
            onLoadMore();
        }
    }

    public abstract void onLoadMore();
}
