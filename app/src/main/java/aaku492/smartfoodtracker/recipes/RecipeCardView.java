package aaku492.smartfoodtracker.recipes;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import aaku492.smartfoodtracker.R;
import aaku492.smartfoodtracker.common.ViewUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-02-20.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipeCardView extends CardView {
    @BindView(R.id.recipe_title)
    protected TextView recipeTitle;

    public RecipeCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static RecipeCardView inflate(LayoutInflater inflater, ViewGroup container) {
        return ViewUtils.inflate(R.layout.recipe_card, inflater, container);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void render(Recipe recipe) {
        recipeTitle.setText(recipe.getTitle());
    }

    public static class RecipeCardViewHolder extends RecyclerView.ViewHolder {
        public RecipeCardViewHolder(View itemView) {
            super(itemView);
        }
    }
}
