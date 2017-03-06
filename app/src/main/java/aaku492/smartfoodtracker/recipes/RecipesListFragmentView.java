package aaku492.smartfoodtracker.recipes;

import android.content.Context;
import android.graphics.Rect;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import aaku492.smartfoodtracker.R;
import aaku492.smartfoodtracker.common.Debouncer;
import aaku492.smartfoodtracker.common.ViewUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-02-20.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipesListFragmentView extends RelativeLayout {
    private static final int NUM_COLS = 2;
    @BindView(R.id.recipes_card_container)
    protected RecyclerView recipesCardContainer;

    @BindView(R.id.recipes_home_root)
    protected View rootView;

    @BindView(R.id.swipe_container)
    protected SwipeRefreshLayout swipeContainer;

    @BindView(R.id.search_recipes)
    protected FloatingActionButton searchButton;

    private Delegate delegate;

    public RecipesListFragmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static RecipesListFragmentView inflate(LayoutInflater inflater, ViewGroup container, Delegate delegate) {
        RecipesListFragmentView view = ViewUtils.inflate(R.layout.fragment_recipes_list, inflater, container);
        view.setDelegate(delegate);
        return view;
    }

    private void setDelegate(final Delegate delegate) {
        this.delegate = delegate;
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                delegate.onRefresh();
            }
        });
        searchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.onSearchPressed();
            }
        });
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        recipesCardContainer.setLayoutManager(new StaggeredGridLayoutManager(NUM_COLS, StaggeredGridLayoutManager.VERTICAL));
        recipesCardContainer.addOnScrollListener(new AutoRefreshingOnScrollListener());

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_dark);
    }

    public void render(RecipesHomeAdapter adapter, boolean inSearchMode) {
        recipesCardContainer.setAdapter(adapter);
        recipesCardContainer.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int space = (int) getContext().getResources().getDimension(R.dimen.recipe_card_margin_half);
                outRect.left = space;
                outRect.right = space;
                outRect.top = space;
                outRect.bottom = space;
            }
        });

        searchButton.setVisibility(inSearchMode ? GONE : VISIBLE);
    }

    public void showMessage(String message) {
        ViewUtils.showMessage(message, rootView);
    }

    public void setRefreshing(boolean isRefreshing) {
        swipeContainer.setRefreshing(isRefreshing);
    }

    public interface Delegate {
        void onLoadMore();
        void onRefresh();
        void onSearchPressed();
    }

    private class AutoRefreshingOnScrollListener extends RecyclerView.OnScrollListener {
        private static final int LOAD_EXTRA = 5;
        private static final long DEBOUNCE_DELAY_MILLISECONDS = 50;
        private Debouncer debouncer = new Debouncer(new Runnable() {
            @Override
            public void run() {
                RecipesListFragmentView.this.post(new Runnable() {
                    @Override
                    public void run() {
                        setRefreshing(true);
                        delegate.onLoadMore();
                    }
                });
            }
        }, DEBOUNCE_DELAY_MILLISECONDS);

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy < 0) {
                return;
            }

            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recipesCardContainer.getLayoutManager();
            int[] visibleItems = layoutManager.findLastVisibleItemPositions(null);
            if (visibleItems[visibleItems.length - 1] + LOAD_EXTRA >= layoutManager.getItemCount()) {

                if (debouncer.finished()) {
                    debouncer.start();
                } else {
                    debouncer.bounce();
                }
            }
        }
    }
}
