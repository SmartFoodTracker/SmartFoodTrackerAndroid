package aaku492.smartfoodtracker.recipes;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-02-20.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipesHomeAdapter extends RecyclerView.Adapter<RecipeCardView.RecipeCardViewHolder> {

    private final ArrayList<RecipeResponse.Recipe> recipes;

    public RecipesHomeAdapter(ArrayList<RecipeResponse.Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public RecipeCardView.RecipeCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeCardView.RecipeCardViewHolder(RecipeCardView.inflate(LayoutInflater.from(parent.getContext()), parent));
    }

    @Override
    public void onBindViewHolder(RecipeCardView.RecipeCardViewHolder holder, int position) {
        ((RecipeCardView) holder.itemView).render(recipes.get(position));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void addAll(List<RecipeResponse.Recipe> recipes) {
        this.recipes.addAll(recipes);
        notifyDataSetChanged();
    }

    public void clear() {
        this.recipes.clear();
        notifyDataSetChanged();
    }

    // Source: http://stackoverflow.com/a/32190325/3817211
    public static class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            outRect.bottom = space;
            outRect.top = 0;

            outRect.left = space/2;
            outRect.right = space/2;

            int spanIndex = ((StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
            int spanCount = ((StaggeredGridLayoutManager)parent.getLayoutManager()).getSpanCount();

            if (parent.getChildAdapterPosition(view) / spanCount == 0) {
                outRect.top = space;
            }

            if (spanIndex == 0) {
                outRect.left = space;
            }

            if (spanIndex == spanCount - 1) {
                outRect.right = space;
            }
        }
    }
}
