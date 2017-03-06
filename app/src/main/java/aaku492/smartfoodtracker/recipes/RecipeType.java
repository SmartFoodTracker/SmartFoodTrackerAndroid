package aaku492.smartfoodtracker.recipes;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-02-20.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public enum RecipeType {
    Any("Any"),
    MainCourse("main course"),
    SideDish("side dish"),
    Dessert("dessert"),
    Appetizer("appetizer"),
    Salad("salad"),
    Bread("bread"),
    Breakfast("breakfast"),
    Soup("soup"),
    Beverage("beverage"),
    Sauce("sauce"),
    Drink("drink");

    private final String description;

    RecipeType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

}
