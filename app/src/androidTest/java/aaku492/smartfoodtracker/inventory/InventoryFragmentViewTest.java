package aaku492.smartfoodtracker.inventory;

import com.facebook.testing.screenshot.Screenshot;
import com.facebook.testing.screenshot.ViewHelpers;

import org.junit.Test;

import java.text.ParseException;

import aaku492.smartfoodtracker.BaseScreenshotTest;

import static aaku492.smartfoodtracker.TestFixtures.getInventoryItems;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-25.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class InventoryFragmentViewTest extends BaseScreenshotTest {
    @Test
    public void testRender() throws ParseException {
        InventoryFragmentView view = InventoryFragmentView.inflate(getLayoutInflater(), null, null);
        InventoryAdapter adapter = new InventoryAdapter(getInventoryItems(), null);
        view.render(adapter);
        adapter.notifyDataSetChanged();
        ViewHelpers.setupView(view)
                .setExactWidthDp(300)
                .setExactHeightDp(400)
                .layout();

        Screenshot.snap(view)
                .record();
    }
}
