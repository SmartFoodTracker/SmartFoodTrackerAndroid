package aaku492.smartfoodtracker;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import aaku492.smartfoodtracker.common.DataProvider;
import aaku492.smartfoodtracker.inventory.InventoryItem;
import aaku492.smartfoodtracker.recipes.Cuisine;
import aaku492.smartfoodtracker.recipes.Ingredients;
import aaku492.smartfoodtracker.recipes.Intolerances;
import aaku492.smartfoodtracker.recipes.RecipeResponse;
import aaku492.smartfoodtracker.recipes.RecipeType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-30.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class FITFragment extends Fragment {

    private static final String LOG_TAG = FITFragment.class.getName();

    private final Object cancellingLock = new Object();
    private boolean isCancelling = false;
    private final List<Call<?>> firedCalls = new ArrayList<>();
    private final CallRegisteringDataProvider dataProvider = new CallRegisteringDataProvider();

    @CallSuper
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof FragmentContainerActivity)) {
            throw new IllegalStateException("The container activity should be " + FragmentContainerActivity.class.getName());
        }
    }

    @CallSuper
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        synchronized (cancellingLock) {
            isCancelling = false;
            if (!firedCalls.isEmpty()) {
                throw new IllegalStateException("Sanity check failure; leftover calls from last time.");
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @CallSuper
    @Override
    public void onStop() {
        super.onStop();
        synchronized (cancellingLock) {
            isCancelling = true;
            Iterator<Call<?>> iterator = firedCalls.iterator();
            while (iterator.hasNext()) {
                Call<?> c = iterator.next();
                if (c.isCanceled() || !c.isExecuted()) {
                    throw new IllegalStateException("The list of fired calls should only contain queued up calls that haven't been cancelled.");
                }
                c.cancel();
                iterator.remove();
            }
        }
    }

    protected String getCurrentDeviceId() {
        return getContainerActivity().getApp().getCurrentDeviceId();
    }

    protected String getUserId() {
        return getContainerActivity().getApp().getUserId();
    }

    protected DataProvider getDataProvider() {
        return dataProvider;
    }

    protected FragmentContainerActivity getContainerActivity() {
        return (FragmentContainerActivity) getActivity();
    }

    protected void pushFragmentActivityForResult(FragmentInitInfo fragmentInitInfo) {
        getContainerActivity().pushFragmentActivityForResult(fragmentInitInfo);
    }

    protected void pushFragmentActivity(FragmentInitInfo fragmentInitInfo) {
        getContainerActivity().pushFragmentActivity(fragmentInitInfo);
    }

    protected void popFragmentActivity() {
        getContainerActivity().popFragmentActivity();
    }

    protected void popFragmentActivityWithResult(int resultCode) {
        getContainerActivity().popFragmentActivityWithResult(resultCode);
    }

    public boolean handleStatusResult(int resultCode) {
        Log.w(LOG_TAG, "Unhandled status result code: " + resultCode);
        return false;
    }

    public boolean onBackPressed() {
        Log.w(LOG_TAG, "Unhandled onBackPressed.");
        return false;
    }

    public boolean onAcceptPressed() {
        Log.w(LOG_TAG, "Unhandled onAcceptPressed.");
        return false;
    }

    public void refresh() {
        Log.w(LOG_TAG, "Unhandled refresh.");
    }

    private class CallRegisteringDataProvider implements DataProvider {

        @Override
        public Call<List<InventoryItem>> getInventory(@Path("deviceId") String deviceId) {
            Call<List<InventoryItem>> call = getContainerActivity().getApp().getDataProvider().getInventory(deviceId);
            firedCalls.add(call);
            return call;
        }

        @Override
        public Call<List<InventoryItem>> deleteItem(@Path("deviceId") String deviceId, @Path("itemId") String itemId) {
            Call<List<InventoryItem>> call = getContainerActivity().getApp().getDataProvider().deleteItem(deviceId, itemId);
            firedCalls.add(call);
            return call;
        }

        @Override
        public Call<InventoryItem> getItem(@Path("deviceId") String deviceId, @Path("itemId") String itemId) {
            Call<InventoryItem> call = getContainerActivity().getApp().getDataProvider().getItem(deviceId, itemId);
            firedCalls.add(call);
            return call;
        }

        @Override
        public Call<List<InventoryItem>> addItem(@Path("deviceId") String deviceId, @Body InventoryItem item) {
            Call<List<InventoryItem>> call = getContainerActivity().getApp().getDataProvider().addItem(deviceId, item);
            firedCalls.add(call);
            return call;
        }

        @Override
        public Call<List<InventoryItem>> editItem(@Path("deviceId") String deviceId, @Path("itemId") String itemId, @Body InventoryItem item) {
            Call<List<InventoryItem>> call = getContainerActivity().getApp().getDataProvider().editItem(deviceId, itemId, item);
            firedCalls.add(call);
            return call;
        }

        @Override
        public Call<RecipeResponse> searchRecipes(@Query("query") String searchQuery, @Query("page") int pageNum, @Query("ingredients") Ingredients ingredients, @Query("cuisine") Cuisine cuisine, @Query("intolerances") Intolerances intolerances, @Query("type") RecipeType type) {
            Call<RecipeResponse> call = getContainerActivity().getApp().getDataProvider().searchRecipes(searchQuery, pageNum, ingredients, cuisine, intolerances, type);
            firedCalls.add(call);
            return call;
        }

        @Override
        public Call<RecipeResponse> getSuggestedRecipes(@Path("userId") String userId, @Query("page") int pageNum) {
            Call<RecipeResponse> call = getContainerActivity().getApp().getDataProvider().getSuggestedRecipes(userId, pageNum);
            firedCalls.add(call);
            return call;
        }
    }

    public abstract class FITRequestCallback<T> implements Callback<T> {

        @Override
        public final void onResponse(Call<T> call, Response<T> response) {
            synchronized (cancellingLock) {
                if (isCancelling) {
                    return;
                }
            }

            if (!firedCalls.remove(call)) {
                throw new IllegalStateException("Completed call was never registered as a fired call.");
            }

            if (response.isSuccessful()) {
                onSuccessfulResponse(response);
            } else {
                onFailure("Got back error response: " + response.code());
            }
        }

        @Override
        public final void onFailure(Call<T> call, Throwable t) {
            synchronized (cancellingLock) {
                if (isCancelling) {
                    return;
                }
            }

            if (!firedCalls.remove(call)) {
                throw new IllegalStateException("Completed call was never registered as a fired call.");
            }
            onFailure("Exception:\n" + t.toString());
        }

        protected abstract void onFailure(String errorDescription);
        protected abstract void onSuccessfulResponse(Response<T> response);
    }
}
