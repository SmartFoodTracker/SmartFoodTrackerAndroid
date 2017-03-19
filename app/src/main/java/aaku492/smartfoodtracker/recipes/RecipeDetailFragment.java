package aaku492.smartfoodtracker.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import aaku492.smartfoodtracker.FITFragment;
import aaku492.smartfoodtracker.FragmentInitInfo;
import aaku492.smartfoodtracker.R;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-02-21.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipeDetailFragment extends FITFragment {
    private static final String RECIPE = "recipe";

    private RecipeResponse.Recipe recipe;

    public static FragmentInitInfo getFragmentInitInfo(RecipeResponse.Recipe recipe) {
        Bundle args = new Bundle();
        args.putSerializable(RECIPE, recipe);
        return new FragmentInitInfo(RecipeDetailFragment.class)
                .setIsModal(false)
                .setIsDetailsScreen(true)
                .setArgs(args);
    }

    @Override
    public RecipeDetailFragmentView onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getContainerActivity().setTitle(R.string.recipe_detail_fragment_title);
        RecipeDetailFragmentView view = RecipeDetailFragmentView.inflate(inflater, container);

        if (savedInstanceState == null || savedInstanceState.getSerializable(RECIPE) == null) {
            this.recipe = (RecipeResponse.Recipe) getArguments().getSerializable(RECIPE);
        } else {
            this.recipe = (RecipeResponse.Recipe) savedInstanceState.getSerializable(RECIPE);
        }
        view.render(this.recipe);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(RECIPE, recipe);
    }

    @Override
    public boolean onBackPressed() {
        popFragmentActivity();
        return true;
    }
}
