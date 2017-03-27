package aaku492.smartfoodtracker.inventory;

import org.junit.Test;

import java.text.ParseException;

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
                try {
                    view.render(getInventoryItems().get(0));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        takeScreenshot(view, 300);
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
        takeScreenshot(view, 300);
    }
}
