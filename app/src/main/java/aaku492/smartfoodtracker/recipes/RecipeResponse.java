package aaku492.smartfoodtracker.recipes;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-02-20.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipeResponse {
    @SerializedName("page")
    private int pageNumber;

    @SerializedName("totalPages")
    private int totalPages;

    @SerializedName("data")
    private ArrayList<Recipe> recipes;

    public RecipeResponse(int pageNumber, int totalPages, ArrayList<Recipe> recipes) {
        this.pageNumber = pageNumber;
        this.totalPages = totalPages;
        this.recipes = recipes;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    public static class Recipe {

        @SerializedName("title")
        private String title;

        @SerializedName("image")
        private String imageUrl;

        @SerializedName("steps")
        private ArrayList<String> steps;

        public Recipe(String title, String imageUrl, ArrayList<String> steps) {
            this.title = title;
            this.imageUrl = imageUrl;
            this.steps = steps;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public ArrayList<String> getSteps() {
            return steps;
        }

        public void setSteps(ArrayList<String> steps) {
            this.steps = steps;
        }
    }
}
