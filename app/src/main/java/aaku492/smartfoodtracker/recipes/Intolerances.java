package aaku492.smartfoodtracker.recipes;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-02-20.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class Intolerances implements Serializable {
    private final List<Intolerance> intolerances;

    public Intolerances(List<Intolerance> intolerances) {
        this.intolerances = intolerances;
    }

    public void add(Intolerance intolerance) {
        this.intolerances.add(intolerance);
    }

    public void remove(Intolerance intolerance) {
        this.intolerances.remove(intolerance);
    }

    @Override
    public String toString() {
        return TextUtils.join(",", intolerances);
    }

    public enum Intolerance {
        Dairy("dairy"),
        Egg("egg"),
        Gluten("gluten"),
        Peanut("peanut"),
        Sesame("sesame"),
        Seafood("Seafood"),
        Shellfish("shellfish"),
        Soy("soy"),
        Sulfite("sulfite"),
        TreeNut("tree nut"),
        Wheat("wheat");

        private final String description;

        Intolerance(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }
    }

}
