package aaku492.smartfoodtracker.recipes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.io.Serializable;

import aaku492.smartfoodtracker.FITFragment;
import aaku492.smartfoodtracker.FragmentInitInfo;
import aaku492.smartfoodtracker.R;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-05.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipeSearchFragment extends FITFragment {
    private static final String QUERY = "query";
    private RecipeSearchQuery query;

    public static FragmentInitInfo getFragmentInitInfo() {
        return new FragmentInitInfo(RecipeSearchFragment.class)
                .setIsModal(true)
                .setIsDetailsScreen(false);
    }

    @Override
    public RecipeSearchFragmentView onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getContainerActivity().setTitle(R.string.recipe_search_fragment_title);
        RecipeSearchFragmentView view = RecipeSearchFragmentView.inflate(inflater, container);

        if (savedInstanceState != null && savedInstanceState.getSerializable(QUERY) != null) {
            this.query = (RecipeSearchQuery) savedInstanceState.getSerializable(QUERY);
        } else {
            this.query = new RecipeSearchQuery();
        }

        view.render(query);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(QUERY, query);
    }

    @Override
    public boolean onBackPressed() {
        popFragmentActivity();
        return true;
    }

    @Override
    public boolean onAcceptPressed() {
        // TODO: search
        getView().showMessage("Coming soon!");
        return true;
    }

    @NonNull
    @Override
    public RecipeSearchFragmentView getView() {
        if (super.getView() == null) {
            throw new IllegalStateException("View not attached when requested: " + RecipeSearchFragment.class.getName());
        }
        return (RecipeSearchFragmentView) super.getView();
    }

    public static class RecipeSearchQuery implements Serializable {
        private String searchQuery;
        private Cuisine cuisine;
        private RecipeType recipeType;

        public RecipeSearchQuery() {
            searchQuery = "";
            cuisine = Cuisine.Any;
            recipeType = RecipeType.Any;
        }


        public String getSearchQuery() {
            return searchQuery;
        }

        public void setSearchQuery(String searchQuery) {
            this.searchQuery = searchQuery;
        }

        public Cuisine getCuisine() {
            return cuisine;
        }

        public void setCuisine(Cuisine cuisine) {
            this.cuisine = cuisine;
        }

        public RecipeType getRecipeType() {
            return recipeType;
        }

        public void setRecipeType(RecipeType recipeType) {
            this.recipeType = recipeType;
        }
    }

}