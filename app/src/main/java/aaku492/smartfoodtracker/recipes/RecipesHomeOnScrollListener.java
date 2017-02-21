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
    private static final int LOAD_EXTRA = 5;

    public RecipesHomeOnScrollListener(StaggeredGridLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (dy < 0) {
            return;
        }

        int[] visibleItems = layoutManager.findFirstVisibleItemPositions(null);
        int firstRowLastCol = visibleItems[visibleItems.length - 1];
        int numItemsFetches = layoutManager.getItemCount();
        if (firstRowLastCol + LOAD_EXTRA >= numItemsFetches) {
            onLoadMore();
        }
    }

    public abstract void onLoadMore();
}
