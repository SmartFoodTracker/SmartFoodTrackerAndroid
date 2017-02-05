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
    @Nullable private final Bundle argsBundle;
    private final boolean isModal;

    public FragmentInitInfo(boolean isModal, @NonNull Class<? extends FITFragment> _class) {
        this(isModal, _class, null);
    }

    public FragmentInitInfo(boolean isModal, @NonNull Class<? extends FITFragment> _class, @Nullable Bundle argsBundle) {
        this.isModal = isModal;
        this.name = _class.getName();
        this.argsBundle = argsBundle;
    }

    @Nullable
    public Bundle getArgsBundle() {
        return argsBundle;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public boolean isModal() {
        return isModal;
    }
}
