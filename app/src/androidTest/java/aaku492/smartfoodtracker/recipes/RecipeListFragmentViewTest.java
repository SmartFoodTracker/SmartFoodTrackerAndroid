package aaku492.smartfoodtracker.recipes;

import org.junit.Test;

import aaku492.smartfoodtracker.BaseScreenshotTest;

import static aaku492.smartfoodtracker.TestFixtures.getRecipes;

/**
 * Created by Andrew Hoskins (ahoskins) on 2017-03-26.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipeListFragmentViewTest  extends BaseScreenshotTest {
    @Test
    public void testRender() {
        final RecipesListFragmentView[] view = new RecipesListFragmentView[1];

        runOnMainSync(new Runnable() {
            @Override
            public void run() {
                view[0] = RecipesListFragmentView.inflate(getLayoutInflater(), null, null);

                final RecipesHomeAdapter adapter = new RecipesHomeAdapter(getRecipes(), null);
                adapter.notifyDataSetChanged();

                view[0].render(adapter, false);
            }
        });

        takeScreenshot(view[0], DEFAULT_SCREENSHOT_WIDTH_DP, 400);
    }
}
