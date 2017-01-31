package aaku492.smartfoodtracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import aaku492.smartfoodtracker.inventory.InventoryFragment;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-28.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: stretch goal: check for the login state here and navigate to other fragments appropriately.
        // MainActivity is just the app entry-point with no view
        startActivity(FragmentContainerActivity.createIntent(this, InventoryFragment.getFragmentInitInfo()));
        finish();
    }
}
