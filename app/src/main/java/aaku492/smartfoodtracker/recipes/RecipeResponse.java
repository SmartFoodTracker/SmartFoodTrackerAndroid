package aaku492.smartfoodtracker.recipes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-02-20.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipeResponse implements Serializable {
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

    public int getTotalPages() {
        return totalPages;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public static class Recipe implements Serializable {

        @SerializedName("title")
        private String title;

        @SerializedName("image")
        private String imageUrl;

        @SerializedName("steps")
        private ArrayList<String> steps;

        @SerializedName("sourceUrl")
        private String sourceUrl;

        @SerializedName("satisfiedIngredients")
        private ArrayList<String> satisfiedIngredients;

        @SerializedName("unsatisfiedIngredients")
        private ArrayList<String> unsatisfiedIngredients;

        public Recipe(String title, String imageUrl, ArrayList<String> steps, String sourceUrl,
                      ArrayList<String> satisfiedIngredients, ArrayList<String> unsatisfiedIngredients) {
            this.title = title;
            this.imageUrl = imageUrl;
            this.steps = steps;
            this.sourceUrl = sourceUrl;
            this.satisfiedIngredients = satisfiedIngredients;
            this.unsatisfiedIngredients = unsatisfiedIngredients;
        }

        public String getTitle() {
            return title;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public ArrayList<String> getSteps() {
            return steps;
        }

        public String getSourceUrl() {
            return sourceUrl;
        }

        public ArrayList<String> getSatisfiedIngredients() {
            return satisfiedIngredients;
        }

        public ArrayList<String> getUnsatisfiedIngredients() {
            return unsatisfiedIngredients;
        }
    }
}
