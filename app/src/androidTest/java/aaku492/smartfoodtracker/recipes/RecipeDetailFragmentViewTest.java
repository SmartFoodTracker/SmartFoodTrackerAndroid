package aaku492.smartfoodtracker.recipes;

import org.junit.Test;

import aaku492.smartfoodtracker.BaseScreenshotTest;

import static aaku492.smartfoodtracker.TestFixtures.getRecipes;

/**
 * Created by Andrew Hoskins (ahoskins) on 2017-03-26.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipeDetailFragmentViewTest  extends BaseScreenshotTest {
    @Test
    public void testRender() {
        final RecipeDetailFragmentView view = RecipeDetailFragmentView.inflate(getLayoutInflater(), null);

        runOnMainSync(new Runnable() {
            @Override
            public void run() {
                view.render(getRecipes().get(0));
            }
        });

        takeScreenshot(view);
    }
}
