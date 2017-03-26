package aaku492.smartfoodtracker;

import android.support.test.InstrumentationRegistry;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import aaku492.smartfoodtracker.inventory.InventoryItem;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-25.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public abstract class BaseScreenshotTest {
    protected LayoutInflater getLayoutInflater() {
        try {
            // Need to manually specify the context to use the App's theme (which works with the design support library)
            // See: https://github.com/facebook/screenshot-tests-for-android/issues/32
            return LayoutInflater.from(new ContextThemeWrapper(InstrumentationRegistry.getTargetContext(), R.style.AppTheme));
        } catch (NullPointerException e) {
            throw new IllegalStateException("The test runner setup is most likely messed up. " +
                    "Check the app module build.gradle and verify that the test runner is " + FITScreenshotTestRunner.class.getName(), e);
        }
    }

    protected static List<InventoryItem> getTestInventoryItems() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.CANADA);
        ArrayList<InventoryItem> items = new ArrayList<>(5);

        items.add(new InventoryItem("Test item 0", "0", 2.3, InventoryItem.Unit.Kilograms,
                dateFormat.parse("01/12/2017").getTime(),
                dateFormat.parse("01/20/2017").getTime()));

        items.add(new InventoryItem("Test item 1", "1", 2d, InventoryItem.Unit.Litres,
                dateFormat.parse("01/12/2016").getTime(),
                dateFormat.parse("01/20/2016").getTime()));

        items.add(new InventoryItem("Test item 2", "2", 2d, InventoryItem.Unit.Pounds,
                dateFormat.parse("01/13/2017").getTime(),
                dateFormat.parse("01/25/2017").getTime()));

        items.add(new InventoryItem("Test item 3", "3", 1.0, InventoryItem.Unit.WholeNumbers,
                dateFormat.parse("02/16/2017").getTime(),
                dateFormat.parse("01/20/2017").getTime()));

        items.add(new InventoryItem("Test item 4", "4", 1.1, InventoryItem.Unit.WholeNumbers,
                null,
                null));

        return items;
    }

    protected void runOnMainSync(Runnable runnable) {
        // This is required for certain inflating/rendering/UI manipulation actions that might only be
        // supported on the main thread/looper threads (e.g. animations).
        // See the related issue: https://github.com/facebook/screenshot-tests-for-android/issues/57
        InstrumentationRegistry.getInstrumentation().runOnMainSync(runnable);
    }
}
