package aaku492.smartfoodtracker.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import aaku492.smartfoodtracker.FITFragment;
import aaku492.smartfoodtracker.FragmentInitInfo;
import aaku492.smartfoodtracker.R;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-02-19.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipesHomeFragment extends FITFragment {

    private RecipesHomeAdapter adapter;

    public static FragmentInitInfo getFragmentInitInfo() {
        return new FragmentInitInfo(false, RecipesHomeFragment.class);
    }

    @Override
    public RecipesHomeFragmentView onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getContainerActivity().setTitle(R.string.recipes_fragment_title);

        ArrayList<Recipe> mockRecipes = new ArrayList<>();
        mockRecipes.add(new Recipe("a title", null, null));
        mockRecipes.add(new Recipe("another title", null, null));
        adapter = new RecipesHomeAdapter(mockRecipes);
        RecipesHomeFragmentView view = RecipesHomeFragmentView.inflate(inflater, container);
        view.render(adapter);
        return view;
    }
}
