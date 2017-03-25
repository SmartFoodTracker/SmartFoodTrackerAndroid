package aaku492.smartfoodtracker.inventory;

import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.testing.screenshot.Screenshot;
import com.facebook.testing.screenshot.ViewHelpers;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import aaku492.smartfoodtracker.BaseScreenshotTest;
import aaku492.smartfoodtracker.R;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-20.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
@RunWith(AndroidJUnit4.class)
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

    private static List<InventoryItem> getTestInventoryItems() throws ParseException {
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
}
