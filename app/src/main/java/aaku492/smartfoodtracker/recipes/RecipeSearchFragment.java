package aaku492.smartfoodtracker.recipes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import aaku492.smartfoodtracker.FITFragment;
import aaku492.smartfoodtracker.FragmentInitInfo;
import aaku492.smartfoodtracker.R;
import aaku492.smartfoodtracker.inventory.InventoryItem;
import retrofit2.Response;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-05.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipeSearchFragment extends FITFragment {
    private static final String QUERY = "query";
    private static final String LOG_TAG = RecipeSearchFragment.class.getName();
    private RecipeSearchQuery query;

    public static FragmentInitInfo getFragmentInitInfo() {
        return new FragmentInitInfo(RecipeSearchFragment.class)
                .setIsModal(true)
                .setIsDetailsScreen(false);
    }

    @Override
    public RecipeSearchFragmentView onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getContainerActivity().setTitle(R.string.recipe_search_fragment_title);
        final RecipeSearchFragmentView view = RecipeSearchFragmentView.inflate(inflater, container);

        if (savedInstanceState != null && savedInstanceState.getSerializable(QUERY) != null) {
            this.query = (RecipeSearchQuery) savedInstanceState.getSerializable(QUERY);
        } else {
            this.query = new RecipeSearchQuery();
        }

        view.setLoading(true);

        getDataProvider().getInventory(getCurrentDeviceId()).enqueue(new FITRequestCallback<List<InventoryItem>>() {
            @Override
            protected void onFailure(String errorDescription) {
                view.setLoading(false);
                Log.e(LOG_TAG, getString(R.string.inventory_fetch_error) + " " + errorDescription);
                getView().showMessage(getString(R.string.inventory_fetch_error));
                view.render(query, null);

            }

            @Override
            protected void onSuccessfulResponse(Response<List<InventoryItem>> response) {
                view.setLoading(false);
                view.render(query, response.body());
            }
        });

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
        getView().onAcceptPressed();
        if (validate()) {
            pushFragmentActivity(RecipesListFragment.getFragmentInitInfo(query));
        } else {
            getView().showMessage(getString(R.string.empty_recipe_search_form));
        }
        return true;
    }

    private boolean validate() {
        return query.getIngredients().getList().size() > 0 || query.getIntolerances().numAdded() > 0 || query.getCuisine() != Cuisine.Any ||
                query.getRecipeType() != RecipeType.Any || !query.getSearchQuery().trim().equals("");
    }

    @NonNull
    @Override
    public RecipeSearchFragmentView getView() {
        if (super.getView() == null) {
            throw new IllegalStateException("View not attached when requested: " + RecipeSearchFragment.class.getName());
        }
        return (RecipeSearchFragmentView) super.getView();
    }
}
