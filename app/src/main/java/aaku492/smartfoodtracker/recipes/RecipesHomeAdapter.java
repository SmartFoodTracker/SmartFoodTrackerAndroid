package aaku492.smartfoodtracker.recipes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-02-20.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipesHomeAdapter extends RecyclerView.Adapter<RecipeCardView.RecipeCardViewHolder> {

    private final ArrayList<RecipeResponse.Recipe> recipes;
    private final Delegate delegate;

    public RecipesHomeAdapter(ArrayList<RecipeResponse.Recipe> recipes, Delegate delegate) {
        this.recipes = recipes;
        this.delegate = delegate;
    }

    @Override
    public RecipeCardView.RecipeCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeCardView.RecipeCardViewHolder(RecipeCardView.inflate(LayoutInflater.from(parent.getContext()), parent, delegate));
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

    public interface Delegate extends RecipeCardView.Delegate {
    }
}
