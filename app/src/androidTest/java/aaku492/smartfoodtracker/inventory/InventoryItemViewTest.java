package aaku492.smartfoodtracker.inventory;

import android.view.View;
import android.widget.LinearLayout;

import com.facebook.testing.screenshot.Screenshot;
import com.facebook.testing.screenshot.ViewHelpers;

import org.junit.Test;

import java.text.ParseException;

import aaku492.smartfoodtracker.BaseScreenshotTest;
import aaku492.smartfoodtracker.R;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-20.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class InventoryItemViewTest extends BaseScreenshotTest {

    @Test
    public void testRenderItems() throws ParseException {
        View rootView = getLayoutInflater().inflate(R.layout.test_inventory_item_view, null, false);
        LinearLayout container = (LinearLayout) rootView.findViewById(R.id.inventory_item_view_container);

        for (InventoryItem item : getTestInventoryItems()) {
            InventoryItemView view = InventoryItemView.inflate(getLayoutInflater(), container, null);
            view.render(item);
            container.addView(view);
        }

        ViewHelpers.setupView(container)
                .setExactWidthDp(300)
                .layout();

        Screenshot.snap(container)
                .record();
    }
}