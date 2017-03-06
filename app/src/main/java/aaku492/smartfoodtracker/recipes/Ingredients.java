package aaku492.smartfoodtracker.recipes;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-02-20.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class Ingredients implements Serializable {
    private final List<String> ingredients;

    public Ingredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void add(String ingredient) {
        this.ingredients.add(ingredient);
    }

    public void remove(String ingredient) {
        this.ingredients.remove(ingredient);
    }

    @Override
    public String toString() {
        return TextUtils.join(",", ingredients);
    }
}
