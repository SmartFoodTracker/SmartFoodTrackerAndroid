package aaku492.smartfoodtracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import aaku492.smartfoodtracker.inventory.InventoryItem;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-26.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class TestFixtures {
    public static List<InventoryItem> getInventoryItems() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.CANADA);
        ArrayList<InventoryItem> items = new ArrayList<>(5);

        try {
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
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        items.add(new InventoryItem("Test item 4", "4", 1.1, InventoryItem.Unit.WholeNumbers,
                null,
                null));

        return items;
    }
}
