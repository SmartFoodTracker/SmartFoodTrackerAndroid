package aaku492.smartfoodtracker.common;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-02-20.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public abstract class SimpleErrorHandlingCallback<T> implements Callback<T> {

    @Override
    public final void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuccessfulResponse(response);
        } else {
            onFailure("Got back error response: " + response.code());
        }
    }

    @Override
    public final void onFailure(Call<T> call, Throwable t) {
        onFailure("Exception:\n" + t.toString());
    }

    protected abstract void onFailure(String errorDescription);
    protected abstract void onSuccessfulResponse(Response<T> response);
}
