package aaku492.smartfoodtracker.inventory;

import org.junit.Test;

import aaku492.smartfoodtracker.BaseScreenshotTest;

import static aaku492.smartfoodtracker.TestFixtures.getInventoryItems;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-25.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class InventoryFragmentViewTest extends BaseScreenshotTest {
    @Test
    public void testRender() {
        InventoryFragmentView view = InventoryFragmentView.inflate(getLayoutInflater(), null, null);
        InventoryAdapter adapter = new InventoryAdapter(getInventoryItems(), null);
        view.render(adapter);
        adapter.notifyDataSetChanged();
        // Need to manually specify the height because RecyclerView fits as much space as provided.
        // If nothing is specified, it keeps the height to 0 + (height of other siblings)
        takeScreenshot(view, DEFAULT_SCREENSHOT_WIDTH_DP, 400);
    }
}
