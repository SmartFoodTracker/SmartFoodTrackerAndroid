package aaku492.smartfoodtracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-30.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class FragmentInitInfo {
    @NonNull private final String name;
    @Nullable private final Intent argsBundle;

    public FragmentInitInfo(@NonNull Class<? extends SFTFragment> _class) {
        this(_class, null);
    }

    public FragmentInitInfo(@NonNull Class<? extends SFTFragment> _class, @Nullable Intent argsBundle) {
        this.name = _class.getName();
        this.argsBundle = argsBundle;
    }

    @Nullable
    public Intent getArgsBundle() {
        return argsBundle;
    }

    @NonNull
    public String getName() {
        return name;
    }
}
