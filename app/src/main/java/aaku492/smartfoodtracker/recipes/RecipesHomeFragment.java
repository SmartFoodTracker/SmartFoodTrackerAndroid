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
public class RecipesHomeFragment extends FITFragment implements RecipesHomeFragmentView.Delegate {

    private RecipesHomeAdapter adapter;
    private ArrayList<RecipeResponse.Recipe> mockRecipes;

    public static FragmentInitInfo getFragmentInitInfo() {
        return new FragmentInitInfo(false, RecipesHomeFragment.class);
    }

    @Override
    public RecipesHomeFragmentView onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getContainerActivity().setTitle(R.string.recipes_fragment_title);


        mockRecipes = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            mockRecipes.add(new RecipeResponse.Recipe("title " + i, null, null));
        }

        adapter = new RecipesHomeAdapter(mockRecipes);
        RecipesHomeFragmentView view = RecipesHomeFragmentView.inflate(inflater, container, this);
        view.render(adapter);
        return view;
    }

    @Override
    public void onLoadMore(final int currentPage) {
        if (currentPage >= 5) {
            return;
        }

        // mock stuff
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                for (int i = 0; i < 10; ++i) {
                    mockRecipes.add(new RecipeResponse.Recipe("title " + (currentPage * 10 + i), null, null));
                }
                getContainerActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyItemRangeInserted(mockRecipes.size() - 10, 10);
                    }
                });
            }
        }).start();
    }
}
