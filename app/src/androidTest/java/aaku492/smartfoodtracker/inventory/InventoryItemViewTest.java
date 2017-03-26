package aaku492.smartfoodtracker.inventory;

import android.view.View;
import android.widget.LinearLayout;

import org.junit.Test;

import java.text.ParseException;

import aaku492.smartfoodtracker.BaseScreenshotTest;
import aaku492.smartfoodtracker.R;

import static aaku492.smartfoodtracker.TestFixtures.getInventoryItems;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-20.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class InventoryItemViewTest extends BaseScreenshotTest {

    @Test
    public void testRender() throws ParseException {
        View rootView = getLayoutInflater().inflate(R.layout.test_inventory_item_view, null, false);
        LinearLayout container = (LinearLayout) rootView.findViewById(R.id.inventory_item_view_container);

        for (InventoryItem item : getInventoryItems()) {
            InventoryItemView view = InventoryItemView.inflate(getLayoutInflater(), container, null);
            view.render(item);
            container.addView(view);
        }

        takeScreenshot(container, 300);
    }
}
