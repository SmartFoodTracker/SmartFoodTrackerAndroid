package aaku492.smartfoodtracker.inventory;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-28.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class InventoryItem implements Serializable {
    @SerializedName("title")
    private String title;

    @SerializedName("_id")
    private String id;

    @SerializedName("quantity")
    private Double quantity;

    @SerializedName("units")
    private Unit units;

    @SerializedName("timeAdded")
    private Long timeAdded;

    @SerializedName("expiryTime")
    private Long expiryTime;

    public InventoryItem(String title, String id, Double quantity, Unit units, Long timeAdded, Long expiryTime) {
        this.title = title;
        this.id = id;
        this.quantity = quantity;
        this.units = units;
        this.timeAdded = timeAdded;
        this.expiryTime = expiryTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Unit getUnits() {
        return units;
    }

    public void setUnits(Unit units) {
        this.units = units;
    }

    public Long getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(Long timeAdded) {
        this.timeAdded = timeAdded;
    }

    public Long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryDate(Long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public enum Unit {
        // The sequence of these strongly typed enum values should align with R.array.item_quantity_units_options string representations
        WholeNumbers("whole"),
        Kilograms("kg"),
        Pounds("lb"),
        Litres("l");

        private final String backingDataString;

        Unit(String backingDataString) {
            this.backingDataString = backingDataString;
        }

        public String getBackingDataString() {
            return backingDataString;
        }

        public static Unit getFromBackingData(String backingDataString) {
            if (WholeNumbers.backingDataString.equals(backingDataString)) {
                return WholeNumbers;
            } else if (Kilograms.backingDataString.equals(backingDataString)) {
                return Kilograms;
            } else if (Pounds.backingDataString.equals(backingDataString)) {
                return Pounds;
            } else if (Litres.backingDataString.equals(backingDataString)) {
                return Litres;
            } else {
                throw new IllegalArgumentException("The backingDataString does not match any known values: " + backingDataString);
            }
        }
    }
}
