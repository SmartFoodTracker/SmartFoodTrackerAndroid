package aaku492.smartfoodtracker.inventory;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-28.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class InventoryItem {
    private final String title;

    public InventoryItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
