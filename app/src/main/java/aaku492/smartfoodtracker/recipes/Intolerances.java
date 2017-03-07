package aaku492.smartfoodtracker.recipes;

import android.support.v4.util.Pair;
import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aaku492.smartfoodtracker.common.FunctionalUtils;

import static aaku492.smartfoodtracker.common.FunctionalUtils.enumerate;
import static aaku492.smartfoodtracker.common.FunctionalUtils.filter;
import static aaku492.smartfoodtracker.common.FunctionalUtils.map;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-02-20.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class Intolerances implements Serializable {
    private final List<Boolean> intoleranceAdded = new ArrayList<>(Intolerance.values().length);

    public Intolerances() {
        for (int i = 0; i < Intolerance.values().length; ++i) {
            intoleranceAdded.add(false);
        }
    }

    public void add(Intolerance intolerance) {
        intoleranceAdded.set(intolerance.ordinal(), true);
    }

    public void remove(Intolerance intolerance) {
        intoleranceAdded.set(intolerance.ordinal(), false);
    }

    @Override
    public String toString() {
        return TextUtils.join(",", map(filter(enumerate(Arrays.asList(Intolerance.values())), new FunctionalUtils.Predicate<Pair<Integer, Intolerance>>() {
            public boolean test(Pair<Integer, Intolerance> in) {
                return intoleranceAdded.get(in.first);
            }
        }), new FunctionalUtils.Mapper<Pair<Integer, Intolerance>, String>() {
            @Override
            public String map(Pair<Integer, Intolerance> in) {
                return in.second.toString();
            }
        }));
    }

    public boolean isAdded(Intolerance intolerance) {
        return intoleranceAdded.get(intolerance.ordinal());
    }

    public enum Intolerance {
        Dairy("dairy"),
        Egg("egg"),
        Gluten("gluten"),
        Peanut("peanut"),
        Sesame("sesame"),
        Seafood("seafood"),
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
