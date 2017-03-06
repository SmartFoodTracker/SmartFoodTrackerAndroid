package aaku492.smartfoodtracker.recipes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import aaku492.smartfoodtracker.FITFragment;
import aaku492.smartfoodtracker.FragmentInitInfo;
import aaku492.smartfoodtracker.R;
import aaku492.smartfoodtracker.common.SimpleErrorHandlingCallback;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-02-19.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipesListFragment extends FITFragment implements RecipesListFragmentView.Delegate, RecipesHomeAdapter.Delegate {

    private static final String QUERY = "query";
    private RecipesHomeAdapter adapter;
    private int totalPages;
    private int currentPageNumber = 1;

    private RecipeSearchQuery query;

    public static FragmentInitInfo getFragmentInitInfo() {
        return new FragmentInitInfo(RecipesListFragment.class)
                .setIsModal(false)
                .setIsDetailsScreen(false);
    }

    public static FragmentInitInfo getFragmentInitInfo(RecipeSearchQuery query) {
        Bundle args = new Bundle();
        args.putSerializable(QUERY, query);
        return new FragmentInitInfo(RecipesListFragment.class)
                .setIsModal(false)
                .setIsDetailsScreen(true)
                .setArgs(args);
    }

    @Override
    public RecipesListFragmentView onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new RecipesHomeAdapter(new ArrayList<RecipeResponse.Recipe>(), this);
        RecipesListFragmentView view = RecipesListFragmentView.inflate(inflater, container, this);


        if (savedInstanceState != null && savedInstanceState.getSerializable(QUERY) != null) {
            this.query = (RecipeSearchQuery) savedInstanceState.getSerializable(QUERY);
        } else if (getArguments() != null && getArguments().getSerializable(QUERY) != null) {
            this.query = (RecipeSearchQuery) getArguments().getSerializable(QUERY);
        } else {
            this.query = null;
        }

        view.render(adapter, isInSearchMode());

        getContainerActivity().setTitle(isInSearchMode() ? R.string.recipe_search_result_title : R.string.recipes_fragment_title);

        fetchRecipes(view, true);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (isInSearchMode()) {
            bundle.putSerializable(QUERY, this.query);
        }
    }

    private void fetchRecipes(final RecipesListFragmentView view, final boolean clear) {
        view.setRefreshing(true);
        Callback<RecipeResponse> callback = new SimpleErrorHandlingCallback<RecipeResponse>() {
            @Override
            protected void onFailure(String errorDescription) {
                view.setRefreshing(false);
                view.showMessage(getString(R.string.recipes_fetch_error));
            }

            @Override
            protected void onSuccessfulResponse(Response<RecipeResponse> response) {
                totalPages = response.body().getTotalPages();
                currentPageNumber = response.body().getPageNumber();
                if (clear) {
                    adapter.clear();
                }
                adapter.addAll(response.body().getRecipes());
                view.setRefreshing(false);
            }
        };

        if (isInSearchMode()) {
            query.makeSearchCall(currentPageNumber, getDataProvider()).enqueue(callback);
        } else {
            getDataProvider().getSuggestedRecipes(getUserId(), currentPageNumber).enqueue(callback);
        }
    }

    @NonNull
    public RecipesListFragmentView getView() {
        if (super.getView() == null) {
            throw new IllegalStateException("View not attached when requested: " + RecipesListFragment.class.getName());
        }
        return (RecipesListFragmentView)super.getView();
    }

    @Override
    public void onLoadMore() {
        if (currentPageNumber < totalPages) {
            ++currentPageNumber;
            fetchRecipes(getView(), false);
        }
    }

    @Override
    public void onRefresh() {
        currentPageNumber = 1;
        fetchRecipes(getView(), true);
    }

    @Override
    public void onSearchPressed() {
        if (isInSearchMode()) {
            throw new IllegalStateException("Search button should've been disabled when in search results mode.");
        }
        pushFragmentActivity(RecipeSearchFragment.getFragmentInitInfo());
    }

    @Override
    public void onRecipeSelected(RecipeResponse.Recipe recipe) {
        pushFragmentActivity(RecipeDetailFragment.getFragmentInitInfo(recipe));
    }

    @Override
    public boolean onBackPressed() {
        if (!isInSearchMode()) {
            return super.onBackPressed();
        }

        popFragmentActivity();
        return true;
    }

    private boolean isInSearchMode() {
        return query != null;
    }
}
