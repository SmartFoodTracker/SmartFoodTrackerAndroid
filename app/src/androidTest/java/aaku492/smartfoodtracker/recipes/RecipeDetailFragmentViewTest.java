package aaku492.smartfoodtracker.recipes;

import org.junit.Test;

import aaku492.smartfoodtracker.BaseScreenshotTest;
import aaku492.smartfoodtracker.common.CollectionUtils;

import static aaku492.smartfoodtracker.TestFixtures.getRecipes;

/**
 * Created by Andrew Hoskins (ahoskins) on 2017-03-26.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipeDetailFragmentViewTest  extends BaseScreenshotTest {
    @Test
    public void testRender() {
        runTest(getRecipes().get(0));
    }

    @Test
    public void testRenderWithoutSourceUrl() {
        runTest(new RecipeResponse.Recipe(
                "Apple pie ",
                "https://image.com",
                CollectionUtils.createArrayList("first", "then", "finally"),
                null,
                CollectionUtils.createArrayList("apple"),
                CollectionUtils.createArrayList("butter")
        ));
    }

    @Test
    public void testRenderWithoutSteps() {
        runTest(new RecipeResponse.Recipe(
                "Apple pie ",
                "https://image.com",
                null,
                "https://food.com",
                CollectionUtils.createArrayList("apple"),
                CollectionUtils.createArrayList("butter")
        ));
    }

    @Test
    public void testRenderWithoutImage() {
        runTest(new RecipeResponse.Recipe(
                "Apple pie ",
                null,
                CollectionUtils.createArrayList("first", "then", "finally"),
                "https://food.com",
                CollectionUtils.createArrayList("apple"),
                CollectionUtils.createArrayList("butter")
        ));
    }

    @Test
    public void testRenderWithoutSatisfiedIngredients() {
        runTest(new RecipeResponse.Recipe(
                "Apple pie ",
                "https://image.com",
                CollectionUtils.createArrayList("first", "then", "finally"),
                "https://food.com",
                null,
                CollectionUtils.createArrayList("butter")
        ));
    }

    @Test
    public void testRenderWithoutUnsatisfiedIngredients() {
        runTest(new RecipeResponse.Recipe(
                "Apple pie ",
                "https://image.com",
                CollectionUtils.createArrayList("first", "then", "finally"),
                "https://food.com",
                CollectionUtils.createArrayList("apple"),
                null
        ));
    }

    private void runTest(final RecipeResponse.Recipe recipe) {
        final RecipeDetailFragmentView view = RecipeDetailFragmentView.inflate(getLayoutInflater(), null);

        runOnMainSync(new Runnable() {
            @Override
            public void run() {
                view.render(recipe);
            }
        });

        takeScreenshot(view);
    }
}
