package aaku492.smartfoodtracker.recipes;

import org.junit.Test;

import aaku492.smartfoodtracker.BaseScreenshotTest;

/**
 * Created by Andrew Hoskins (ahoskins) on 2017-03-26.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipeSearchFragmentViewTest extends BaseScreenshotTest {
    @Test
    public void testRenderWithoutInventory() {
        final RecipeSearchFragmentView view = RecipeSearchFragmentView.inflate(getLayoutInflater(), null);

        runOnMainSync(new Runnable() {
            @Override
            public void run() {
                RecipeSearchQuery query = new RecipeSearchQuery("1");
                query.setSearchQuery("tacos");
                query.setRecipeType(RecipeType.Any);
                query.setCuisine(Cuisine.Caribbean);

                view.render(query, null);
            }
        });

        takeScreenshot(view);
    }
}
