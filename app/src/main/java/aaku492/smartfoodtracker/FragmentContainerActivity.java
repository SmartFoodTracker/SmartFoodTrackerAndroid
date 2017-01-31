package aaku492.smartfoodtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-30.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class FragmentContainerActivity extends AppCompatActivity {

    private static final String LOG_TAG = FragmentContainerActivity.class.getName();
    private static final String FRAGMENT_NAME = "fragment_name";
    private static final String FRAGMENT_BUNDLE_ARG = "fragment_bundle_arg";

    public static Intent createIntent(Context creatingContext, FragmentInitInfo fragmentInitInfo) {
        Intent intent = new Intent(creatingContext, FragmentContainerActivity.class);
        intent.putExtra(FRAGMENT_NAME, fragmentInitInfo.getName());
        if (fragmentInitInfo.getArgsBundle() != null) {
            intent.putExtra(FRAGMENT_BUNDLE_ARG, fragmentInitInfo.getArgsBundle());
        }
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);


        Intent intent = getIntent();
        String fragmentName = intent.getStringExtra(FRAGMENT_NAME);

        Fragment fragment = createFragment(fragmentName);
        if (intent.hasExtra(FRAGMENT_BUNDLE_ARG)) {
            fragment.setArguments(intent.getBundleExtra(FRAGMENT_BUNDLE_ARG));
        }
        getSupportFragmentManager().beginTransaction().add(R.id.activity_fragment_root, fragment, fragmentName).commit();
    }

    @NonNull
    private static Fragment createFragment(String fragmentName) {
        Fragment fragment;
        try {
            Object o = Class.forName(fragmentName).newInstance();
            if (!(o instanceof Fragment)) {
                throw new ClassCastException("The fragment name should be a Fragment object: " + fragmentName);
            }
            fragment = (Fragment)o;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Don't have access to create class " + fragmentName, e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Fragment does not have the default constructor: " + fragmentName, e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found: " + fragmentName, e);
        }
        return fragment;
    }

    public void setTitle(String title) {
        if (getSupportActionBar() == null) {
            Log.e(LOG_TAG, "You messed up! Action bar should've been present. Unable to set title to: " + title);
            return;
        }

        getSupportActionBar().setTitle(title);
    }

    public App getApp() {
        return (App) super.getApplication();
    }

    public void pushFragment(FragmentInitInfo fragmentInitInfo) {
        startActivity(createIntent(this, fragmentInitInfo));
    }
}
