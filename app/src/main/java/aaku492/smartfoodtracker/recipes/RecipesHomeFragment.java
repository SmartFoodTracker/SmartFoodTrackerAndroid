package aaku492.smartfoodtracker.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import aaku492.smartfoodtracker.FITFragment;
import aaku492.smartfoodtracker.FragmentInitInfo;
import aaku492.smartfoodtracker.R;
import aaku492.smartfoodtracker.common.SimpleErrorHandlingCallback;
import retrofit2.Response;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-02-19.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipesHomeFragment extends FITFragment implements RecipesHomeFragmentView.Delegate {

    private RecipesHomeAdapter adapter;
    private int totalPages;
    private int currentPageNumber = 0;

    public static FragmentInitInfo getFragmentInitInfo() {
        return new FragmentInitInfo(false, RecipesHomeFragment.class);
    }

    @Override
    public RecipesHomeFragmentView onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new RecipesHomeAdapter(new ArrayList<RecipeResponse.Recipe>());
        RecipesHomeFragmentView view = RecipesHomeFragmentView.inflate(inflater, container, this);
        view.render(adapter);

        getContainerActivity().setTitle(R.string.recipes_fragment_title);

        fetchRecipes();
        return view;
    }

    private void fetchRecipes() {
        // TODO: view.setRefreshing(true)
        getDataProvider().getSuggestedRecipes(getUserId(), currentPageNumber).enqueue(new SimpleErrorHandlingCallback<RecipeResponse>() {
            @Override
            protected void onFailure(String errorDescription) {
                // TODO: view.setRefreshing(false)
                //noinspection ConstantConditions
                ((RecipesHomeFragmentView)getView()).showMessage(getString(R.string.recipes_fetch_error));
            }

            @Override
            protected void onSuccessfulResponse(Response<RecipeResponse> response) {
                totalPages = response.body().getTotalPages();
                currentPageNumber = response.body().getPageNumber();
                adapter.addAll(response.body().getRecipes());
                // TODO: view.setRefreshing(false)
            }
        });
    }

    @Override
    public void onLoadMore() {
        if (currentPageNumber < totalPages - 1) {
            ++currentPageNumber;
            fetchRecipes();
        }
    }
}
