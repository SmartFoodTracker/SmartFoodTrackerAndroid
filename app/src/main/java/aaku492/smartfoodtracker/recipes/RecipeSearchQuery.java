package aaku492.smartfoodtracker.recipes;

import java.io.Serializable;

import aaku492.smartfoodtracker.common.DataProvider;
import retrofit2.Call;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-06.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipeSearchQuery implements Serializable {
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

    public Call<RecipeResponse> makeSearchCall(int pageNumber, DataProvider dataProvider) {
        String searchQuery = this.searchQuery == null || this.searchQuery.trim().equals("") ? null : this.searchQuery.trim();

        // TODO
//            Ingredients ingredients = null;
//            Intolerances intolerances = null;

        Cuisine cuisine = this.cuisine == Cuisine.Any ? null : this.cuisine;
        RecipeType recipeType = this.recipeType == RecipeType.Any ? null : this.recipeType;

        return dataProvider.searchRecipes(searchQuery, pageNumber, null, cuisine, null, recipeType);
    }
}
