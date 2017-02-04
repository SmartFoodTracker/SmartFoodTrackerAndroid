package aaku492.smartfoodtracker;

import android.app.Application;

import aaku492.smartfoodtracker.common.DataProvider;
import aaku492.smartfoodtracker.common.NetworkManager;
import aaku492.smartfoodtracker.inventory.InventoryItem;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-29.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class App extends Application {
    private static final int MAX_AGE_ONLINE_SECONDS = 60; // 1 min
    private static final int MAX_STALE_OFFLINE_SECONDS = 7*4*24*60*60; // 4 weeks
    private static final int CACHE_SIZE_BYTES = 10*1024*1024; // 10 MB
    private static final String CACHE_DIR = "responses";

    private DataProvider dataProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        if (getResources().getStringArray(R.array.item_quantity_units_options).length != InventoryItem.Unit.values().length) {
            throw new IllegalStateException("The enum InventoryItem.Unit and the string array resource item_quantity_units_options" +
            " need to have a 1-1 ordered correspondence.");
        }

        this.dataProvider = new NetworkManager<>(
                getApplicationContext(),
                CACHE_SIZE_BYTES,
                MAX_AGE_ONLINE_SECONDS,
                MAX_STALE_OFFLINE_SECONDS,
                CACHE_DIR,
                getString(R.string.backend_api_url),
                DataProvider.class)
                .getDataProvider();
    }

    public DataProvider getDataProvider() {
        return dataProvider;
    }

    public String getCurrentDeviceId() {
        // TODO: implementing this properly is a stretch goal
        return "1";
    }
}
