package aaku492.smartfoodtracker.recipes;

import org.junit.Test;

import aaku492.smartfoodtracker.BaseScreenshotTest;

import static aaku492.smartfoodtracker.TestFixtures.getRecipes;

/**
 * Created by Andrew Hoskins (ahoskins) on 2017-03-26.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipeFragmentViewTest  extends BaseScreenshotTest {
    @Test
    public void testRender() {

        runOnMainSync(new Runnable() {
            @Override
            public void run() {
                final RecipesListFragmentView view = RecipesListFragmentView.inflate(getLayoutInflater(), null, null);
                final RecipesHomeAdapter adapter = new RecipesHomeAdapter(getRecipes(), null);
                adapter.notifyDataSetChanged();
                view.render(adapter, false);

                takeScreenshot(view, DEFAULT_SCREENSHOT_WIDTH_DP, 400);
            }
        });
    }
}
