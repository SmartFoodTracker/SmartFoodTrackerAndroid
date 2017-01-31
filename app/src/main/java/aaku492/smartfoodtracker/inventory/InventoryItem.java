package aaku492.smartfoodtracker.inventory;

import java.util.Date;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-28.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class InventoryItem {
    private final String title;
    private final String id;
    private final Double quantity;
    private final String units;
    private final Date dateAdded;
    private final Date expiryDate;

    public InventoryItem(String title, String id, Double quantity, String units, Date dateAdded, Date expiryDate) {
        this.title = title;
        this.id = id;
        this.quantity = quantity;
        this.units = units;
        this.dateAdded = dateAdded;
        this.expiryDate = expiryDate;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public String getUnits() {
        return units;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
}
