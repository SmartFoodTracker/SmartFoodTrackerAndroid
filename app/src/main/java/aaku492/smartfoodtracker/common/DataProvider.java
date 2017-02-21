package aaku492.smartfoodtracker.common;

import java.util.List;

import aaku492.smartfoodtracker.inventory.InventoryItem;
import aaku492.smartfoodtracker.recipes.Cuisine;
import aaku492.smartfoodtracker.recipes.Ingredients;
import aaku492.smartfoodtracker.recipes.Intolerances;
import aaku492.smartfoodtracker.recipes.RecipeResponse;
import aaku492.smartfoodtracker.recipes.RecipeType;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-29.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public interface DataProvider {
//    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @GET("{deviceId}/inventory")
    Call<List<InventoryItem>> getInventory(@Path("deviceId") String deviceId);

    @DELETE("{deviceId}/inventory/{itemId}")
    Call<List<InventoryItem>> deleteItem(@Path("deviceId") String deviceId, @Path("itemId") String itemId);

//    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @GET("{deviceId}/inventory/{itemId}")
    Call<InventoryItem> getItem(@Path("deviceId") String deviceId, @Path("itemId") String itemId);

    @PUT("{deviceId}/inventory")
    Call<List<InventoryItem>> addItem(@Path("deviceId") String deviceId, @Body InventoryItem item);

    @PUT("{deviceId}/inventory/{itemId}")
    Call<List<InventoryItem>> editItem(@Path("deviceId") String deviceId, @Path("itemId") String itemId, @Body InventoryItem item);

    @GET("/recipes")
    Call<RecipeResponse> searchRecipes(@Query("query") String searchQuery,
                                       @Query("page") int pageNum,
                                       @Query("ingredients") Ingredients ingredients,
                                       @Query("cuisine") Cuisine cuisine,
                                       @Query("intolerances") Intolerances intolerances,
                                       @Query("type") RecipeType type);
}
