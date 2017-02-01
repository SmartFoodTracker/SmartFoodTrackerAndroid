package aaku492.smartfoodtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-30.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class FragmentContainerActivity extends AppCompatActivity {

    private static final String LOG_TAG = FragmentContainerActivity.class.getName();
    private static final String FRAGMENT_NAME = "fragment_name";
    private static final String FRAGMENT_BUNDLE_ARG = "fragment_bundle_arg";
    private static final String IS_MODAL = "is_modal";
    private static final int ACTIVITY_STATUS_REQ_CODE = 0;
    public static final int RESULT_ERROR = 1010101;
    private boolean isModal;

    public static Intent createIntent(Context creatingContext, FragmentInitInfo fragmentInitInfo) {
        Intent intent = new Intent(creatingContext, FragmentContainerActivity.class);
        intent.putExtra(FRAGMENT_NAME, fragmentInitInfo.getName());
        if (fragmentInitInfo.getArgsBundle() != null) {
            intent.putExtra(FRAGMENT_BUNDLE_ARG, fragmentInitInfo.getArgsBundle());
        }
        intent.putExtra(IS_MODAL, fragmentInitInfo.isModal());
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        if (savedInstanceState == null || getCurrentFragment() == null) {
            // current fragment can be null if the activity was destroyed because the device ran out of memory.
            Intent intent = getIntent();
            String fragmentName = intent.getStringExtra(FRAGMENT_NAME);

            SFTFragment fragment = createFragment(fragmentName);
            if (intent.hasExtra(FRAGMENT_BUNDLE_ARG)) {
                fragment.setArguments(intent.getBundleExtra(FRAGMENT_BUNDLE_ARG));
            }
            getSupportFragmentManager().beginTransaction().add(R.id.activity_fragment_root, fragment, fragmentName).commit();
            this.isModal = intent.getBooleanExtra(IS_MODAL, false);
        } else {
            // else, orientation change. No need to re-recreate the fragment. The fragment should've saved it's bundle.
            this.isModal = savedInstanceState.getBoolean(IS_MODAL);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(IS_MODAL, this.isModal);
    }

    @NonNull
    private static SFTFragment createFragment(String fragmentName) {
        SFTFragment fragment;
        try {
            Object o = Class.forName(fragmentName).newInstance();
            if (!(o instanceof SFTFragment)) {
                throw new ClassCastException("The fragment name should be a Fragment object: " + fragmentName);
            }
            fragment = (SFTFragment) o;
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
        startActivityForResult(createIntent(this, fragmentInitInfo), ACTIVITY_STATUS_REQ_CODE);
    }

    public void popFragment(int resultCode) {
        Intent returnIntent = new Intent();
        setResult(resultCode, returnIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACTIVITY_STATUS_REQ_CODE:
                getCurrentFragment().handleStatusResult(resultCode);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private SFTFragment getCurrentFragment() {
        return (SFTFragment) getSupportFragmentManager().findFragmentById(R.id.activity_fragment_root);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isModal) {
            //noinspection ConstantConditions
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getMenuInflater().inflate(R.menu.menu_modal, menu);
            return true;
        } else {
            return super.onCreateOptionsMenu(menu);
        }
    }

    @Override
    public void onBackPressed() {
        if (!isModal || !getCurrentFragment().onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isModal) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    return getCurrentFragment().onBackPressed();
                case R.id.accept:
                    return getCurrentFragment().onAcceptPressed();
                default:
                    return super.onOptionsItemSelected(item);
            }
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}