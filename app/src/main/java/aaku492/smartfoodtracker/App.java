package aaku492.smartfoodtracker;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import aaku492.smartfoodtracker.common.DataProvider;
import aaku492.smartfoodtracker.inventory.InventoryItem;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-29.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class App extends Application {
    private DataProvider dataProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        if (getResources().getStringArray(R.array.item_quantity_units_options).length != InventoryItem.Unit.values().length) {
            throw new IllegalStateException("The enum InventoryItem.Unit and the string array resource item_quantity_units_options" +
            " need to have a 1-1 ordered correspondence.");
        }

        this.dataProvider = buildDataProvider();
    }

    public DataProvider getDataProvider() {
        return dataProvider;
    }

    public String getCurrentDeviceId() {
        // TODO: implementing this properly is a stretch goal
        return "1";
    }

    public String getUserId() {
        // TODO: implementing this properly is a stretch goal
        return "1";
    }

    private DataProvider buildDataProvider() {

        Gson gson = new GsonBuilder()
                .setDateFormat(getApplicationContext().getString(R.string.date_format_backend_api))
                .registerTypeAdapter(InventoryItem.Unit.class, new JsonDeserializer<InventoryItem.Unit>() {
                    @Override
                    public InventoryItem.Unit deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext jsonContext) throws JsonParseException {
                        try {
                            return InventoryItem.Unit.getFromBackingData(json.getAsString());
                        } catch (IllegalArgumentException e) {
                            throw new JsonParseException(e);
                        }
                    }
                })
                .registerTypeAdapter(InventoryItem.Unit.class, new JsonSerializer<InventoryItem.Unit>() {
                    @Override
                    public JsonElement serialize(InventoryItem.Unit src, Type typeOfSrc, JsonSerializationContext context) {
                        return new JsonPrimitive(src.getBackingDataString());
                    }
                })
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.backend_api_url))
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(DataProvider.class);
    }
}
