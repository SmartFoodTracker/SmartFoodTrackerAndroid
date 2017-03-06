package aaku492.smartfoodtracker.recipes;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-02-20.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public enum Cuisine {
    Any("Any"),
    African("african"),
    Chinese("chinese"),
    Japanese("japanese"),
    Korean("korean"),
    Vietnamese("vietnamese"),
    Thai("thai"),
    Indian("indian"),
    British("british"),
    Irish("irish"),
    French("french"),
    Italian("italian"),
    Mexican("mexican"),
    Spanish("spanish"),
    MiddleEastern("middle eastern"),
    Jewish("jewish"),
    American("american"),
    Cajun("cajun"),
    Southern("southern"),
    Greek("greek"),
    German("german"),
    Nordic("nordic"),
    EasternEuropean("eastern european"),
    Caribbean("caribbean"),
    LatinAmerican("latin american");


    private final String description;

    Cuisine(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

}
