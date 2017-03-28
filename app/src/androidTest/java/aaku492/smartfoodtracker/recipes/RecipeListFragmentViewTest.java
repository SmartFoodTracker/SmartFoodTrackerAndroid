package aaku492.smartfoodtracker.recipes;

import org.junit.Test;

import java.util.ArrayList;

import aaku492.smartfoodtracker.BaseScreenshotTest;

import static aaku492.smartfoodtracker.BaseScreenshotTest.ScreenshotTaker.DEFAULT_DELAY_MS;
import static aaku492.smartfoodtracker.BaseScreenshotTest.ScreenshotTaker.DEFAULT_SCREENSHOT_HEIGHT_PX;
import static aaku492.smartfoodtracker.BaseScreenshotTest.ScreenshotTaker.DEFAULT_SCREENSHOT_WIDTH_PX;
import static aaku492.smartfoodtracker.TestFixtures.getRecipes;

/**
 * Created by Andrew Hoskins (ahoskins) on 2017-03-26.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipeListFragmentViewTest extends BaseScreenshotTest {
    @Test
    public void testRender() {
        runTest(new RecipesHomeAdapter(getRecipes(), null));
    }

    @Test
    public void testRenderWithNoRecipes() {
        runTest(new RecipesHomeAdapter(new ArrayList<RecipeResponse.Recipe>(), null));
    }

    private void runTest(final RecipesHomeAdapter adapter) {
        final RecipesListFragmentView[] view = new RecipesListFragmentView[1];

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
                .setWidthPx(DEFAULT_SCREENSHOT_WIDTH_PX)
                .setHeightPx(DEFAULT_SCREENSHOT_HEIGHT_PX)
                .setDelay(DEFAULT_DELAY_MS)
                .layout(true)
                .record();
    }
}
