package aaku492.smartfoodtracker.common;

import java.util.List;

import aaku492.smartfoodtracker.inventory.InventoryItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-29.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public interface DataProvider {
    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @GET("{deviceId}/inventory")
    Call<List<InventoryItem>> getInventory(@Path("deviceId") String deviceId);
}
