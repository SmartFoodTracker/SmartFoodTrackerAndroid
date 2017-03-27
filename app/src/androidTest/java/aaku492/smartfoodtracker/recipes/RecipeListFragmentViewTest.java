package aaku492.smartfoodtracker.recipes;

import org.junit.Test;

import aaku492.smartfoodtracker.BaseScreenshotTest;

import static aaku492.smartfoodtracker.BaseScreenshotTest.ScreenshotTaker.DEFAULT_DELAY_MS;
import static aaku492.smartfoodtracker.BaseScreenshotTest.ScreenshotTaker.DEFAULT_SCREENSHOT_WIDTH_DP;
import static aaku492.smartfoodtracker.TestFixtures.getRecipes;

/**
 * Created by Andrew Hoskins (ahoskins) on 2017-03-26.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipeListFragmentViewTest  extends BaseScreenshotTest {
    @Test
    public void testRender() {
        final RecipesListFragmentView[] view = new RecipesListFragmentView[1];
        final RecipesHomeAdapter adapter = new RecipesHomeAdapter(getRecipes(), null);

        runOnMainSync(new Runnable() {
            @Override
            public void run() {
                view[0] = RecipesListFragmentView.inflate(getLayoutInflater(), null, null);
                adapter.notifyDataSetChanged();
                view[0].render(adapter, false);
            }
        });

        // Can't call takeScreenshot directly, because we need to layout on main thread.
        // Glide doesn't do its rendering until the layout stage
        new ScreenshotTaker(view[0])
                .setWidthDp(DEFAULT_SCREENSHOT_WIDTH_DP)
                .setHeightDp(400)
                .setDelay(DEFAULT_DELAY_MS)
                .layout(true)
                .record();
    }
}
