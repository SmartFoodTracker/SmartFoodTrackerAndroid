package aaku492.smartfoodtracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-30.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class FragmentInitInfo {
    @NonNull private final String name;
    @Nullable private Bundle argsBundle = null;
    private boolean isModal = false;
    private boolean isDetailsScreen = false;

    public FragmentInitInfo(@NonNull Class<? extends FITFragment> _class) {
        this.name = _class.getName();
    }

    @NonNull
    public String getName() {
        return name;
    }

    public FragmentInitInfo setIsModal(boolean isModal) {
        this.isModal = isModal;
        return this;
    }

    public boolean getIsModal() {
        return isModal;
    }

    public FragmentInitInfo setArgs(@Nullable Bundle args) {
        this.argsBundle = args;
        return this;
    }

    @Nullable
    public Bundle getArgs() {
        return argsBundle;
    }

    public FragmentInitInfo setIsDetailsScreen(boolean isDetailsScreen) {
        this.isDetailsScreen = isDetailsScreen;
        return this;
    }

    public boolean getIsDetailsScreen() {
        return isDetailsScreen;
    }
}
