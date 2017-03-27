package aaku492.smartfoodtracker.recipes;

import android.support.test.espresso.core.deps.guava.util.concurrent.AbstractScheduledService;
import android.widget.Spinner;

import com.google.android.flexbox.FlexboxLayout;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import aaku492.smartfoodtracker.BaseScreenshotTest;
import aaku492.smartfoodtracker.R;
import aaku492.smartfoodtracker.common.Field;
import aaku492.smartfoodtracker.inventory.InventoryItem;

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

    @Test
    public void testRenderWithInventory() {
        final RecipeSearchFragmentView view = RecipeSearchFragmentView.inflate(getLayoutInflater(), null);

        final List<InventoryItem> inventoryItemList = new ArrayList<>();
        inventoryItemList.add(
                new InventoryItem(
                        "banana",
                        "2",
                        1d,
                        InventoryItem.Unit.WholeNumbers,
                        143L,
                        143L
                )
        );
        inventoryItemList.add(
                new InventoryItem(
                        "peaches",
                        "4",
                        1d,
                        InventoryItem.Unit.WholeNumbers,
                        143L,
                        143L
                )
        );

        runOnMainSync(new Runnable() {
            @Override
            public void run() {
                RecipeSearchQuery query = new RecipeSearchQuery("1");
                query.setSearchQuery("tacos");
                query.setRecipeType(RecipeType.Any);
                query.setCuisine(Cuisine.Caribbean);

                view.render(query, inventoryItemList);

                view.findViewById(R.id.ingredients_field).setSelected(true);
            }
        });

        takeScreenshot(view);
    }

    @Test
    public void testRenderWithInventoryText() {
        final RecipeSearchFragmentView view = RecipeSearchFragmentView.inflate(getLayoutInflater(), null);

        runOnMainSync(new Runnable() {
            @Override
            public void run() {
                RecipeSearchQuery query = new RecipeSearchQuery("1");
                query.setSearchQuery("tacos");
                query.setRecipeType(RecipeType.Any);
                query.setCuisine(Cuisine.Caribbean);

                view.render(query, null);

                ((Field) view.findViewById(R.id.ingredients_field)).setText("ban");
            }
        });

        takeScreenshot(view);
    }

    @Test
    public void testRenderWithQueryText() {
        final RecipeSearchFragmentView view = RecipeSearchFragmentView.inflate(getLayoutInflater(), null);

        runOnMainSync(new Runnable() {
            @Override
            public void run() {
                RecipeSearchQuery query = new RecipeSearchQuery("1");
                query.setSearchQuery("tacos");
                query.setRecipeType(RecipeType.Any);
                query.setCuisine(Cuisine.Caribbean);

                view.render(query, null);

                ((Field) view.findViewById(R.id.recipe_search_field)).setText("pudding");
            }
        });

        takeScreenshot(view);
    }

    @Test
    public void testRenderWithCuisineSelected() {
        final RecipeSearchFragmentView view = RecipeSearchFragmentView.inflate(getLayoutInflater(), null);

        runOnMainSync(new Runnable() {
            @Override
            public void run() {
                RecipeSearchQuery query = new RecipeSearchQuery("1");
                query.setSearchQuery("tacos");
                query.setRecipeType(RecipeType.Any);
                query.setCuisine(Cuisine.Caribbean);

                view.render(query, null);

                ((Spinner) view.findViewById(R.id.cuisine)).setSelection(Cuisine.American.ordinal());
            }
        });

        takeScreenshot(view);
    }

    @Test
    public void testRenderWithRecipeTypeSelected() {
        final RecipeSearchFragmentView view = RecipeSearchFragmentView.inflate(getLayoutInflater(), null);

        runOnMainSync(new Runnable() {
            @Override
            public void run() {
                RecipeSearchQuery query = new RecipeSearchQuery("1");
                query.setSearchQuery("tacos");
                query.setRecipeType(RecipeType.Any);
                query.setCuisine(Cuisine.Caribbean);

                view.render(query, null);

                ((Spinner) view.findViewById(R.id.recipe_type)).setSelection(RecipeType.Bread.ordinal());
            }
        });

        takeScreenshot(view);
    }

    @Test
    public void testRenderWithIntolerancesSelected() {
        final RecipeSearchFragmentView view = RecipeSearchFragmentView.inflate(getLayoutInflater(), null);

        runOnMainSync(new Runnable() {
            @Override
            public void run() {
                RecipeSearchQuery query = new RecipeSearchQuery("1");
                query.setSearchQuery("tacos");
                query.setRecipeType(RecipeType.Any);
                query.setCuisine(Cuisine.Caribbean);

                view.render(query, null);

                ((FlexboxLayout) view.findViewById(R.id.intolerances_container))
                        .getChildAt(0).setSelected(true);
            }
        });

        takeScreenshot(view);
    }
}
