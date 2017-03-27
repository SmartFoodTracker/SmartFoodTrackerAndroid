package aaku492.smartfoodtracker.inventory;

import org.junit.Test;

import aaku492.smartfoodtracker.BaseScreenshotTest;

import static aaku492.smartfoodtracker.TestFixtures.getInventoryItems;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-25.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class AddEditItemFragmentViewTest extends BaseScreenshotTest {

    @Test
    public void testRenderEditItem() {
        final AddEditItemFragmentView view = AddEditItemFragmentView.inflate(getLayoutInflater(), null, null);
        runOnMainSync(new Runnable() {
            @Override
            public void run() {
                view.render(getInventoryItems().get(0));
            }
        });

        takeScreenshot(view);
    }

    @Test
    public void testRenderNewItem() {
        final AddEditItemFragmentView view = AddEditItemFragmentView.inflate(getLayoutInflater(), null, null);
        runOnMainSync(new Runnable() {
            @Override
            public void run() {
                view.render(new InventoryItem(null, null, 1.00, InventoryItem.Unit.values()[0], null, null));
            }
        });
        takeScreenshot(view);
    }
}
