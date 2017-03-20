package aaku492.smartfoodtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import aaku492.smartfoodtracker.about.AboutFragment;
import aaku492.smartfoodtracker.inventory.InventoryFragment;
import aaku492.smartfoodtracker.recipes.RecipesListFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-01-30.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class FragmentContainerActivity extends AppCompatActivity {
    private static final String LOG_TAG = FragmentContainerActivity.class.getName();
    private static final String FRAGMENT_NAME = "fragment_name";
    private static final String FRAGMENT_BUNDLE_ARG = "fragment_bundle_arg";
    private static final String IS_MODAL = "is_modal";
    private static final String IS_DETAILS_SCREEN = "is_details_screen";
    private static final int ACTIVITY_STATUS_REQ_CODE = 0;
    public static final int RESULT_ERROR = 1010101;
    private boolean isModal;
    private boolean isDetailsScreen;

    @BindView(R.id.bottom_navigation)
    protected BottomNavigationView bottomNavigationView;

    public static Intent createIntent(Context creatingContext, FragmentInitInfo fragmentInitInfo) {
        Intent intent = new Intent(creatingContext, FragmentContainerActivity.class);
        intent.putExtra(FRAGMENT_NAME, fragmentInitInfo.getName());
        if (fragmentInitInfo.getArgs() != null) {
            intent.putExtra(FRAGMENT_BUNDLE_ARG, fragmentInitInfo.getArgs());
        }
        intent.putExtra(IS_MODAL, fragmentInitInfo.getIsModal());
        intent.putExtra(IS_DETAILS_SCREEN, fragmentInitInfo.getIsDetailsScreen());
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        ButterKnife.bind(this);

        if (savedInstanceState == null || getCurrentFragment() == null) {
            // current fragment can be null if the activity was destroyed because the device ran out of memory.
            loadFragment(getIntent().getStringExtra(FRAGMENT_NAME),
                    getIntent().getBooleanExtra(IS_MODAL, false),
                    getIntent().getBooleanExtra(IS_DETAILS_SCREEN, false),
                    getIntent().hasExtra(FRAGMENT_BUNDLE_ARG) ? getIntent().getBundleExtra(FRAGMENT_BUNDLE_ARG) : null);
        } else {
            // else, orientation change. No need to re-recreate the fragment. The fragment should've saved it's bundle.
            this.isModal = savedInstanceState.getBoolean(IS_MODAL);
            this.isDetailsScreen = savedInstanceState.getBoolean(IS_DETAILS_SCREEN);
        }

        if (this.isDetailsScreen || this.isModal) {
            bottomNavigationView.setEnabled(false);
            bottomNavigationView.setVisibility(View.GONE);
        } else {
            bottomNavigationView.setEnabled(true);
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    FragmentInitInfo nextFragment;
                    switch (item.getItemId()) {
                        case R.id.action_inventory:
                            nextFragment = InventoryFragment.getFragmentInitInfo();
                            break;
                        case R.id.action_recipes:
                            nextFragment = RecipesListFragment.getFragmentInitInfo();
                            break;
                        case R.id.action_about:
                            nextFragment = AboutFragment.getFragmentInfo();
                            break;
                        default:
                            Log.e(LOG_TAG, "Unknown bottom nav menu item with id: " + item.getItemId());
                            return false;

                    }

                    if (nextFragment.getName().equals(getCurrentFragment().getClass().getName())) {
                        getCurrentFragment().refresh();
                    } else {
                        loadFragment(nextFragment.getName(), nextFragment.getIsModal(), nextFragment.getIsDetailsScreen(), nextFragment.getArgs());
                    }
                    return true;
                }
            });
        }
    }

    private void loadFragment(String fragmentName, boolean isModal, boolean isDetailsScreen, @Nullable Bundle args) {
        FITFragment fragment = createFragment(fragmentName);
        if (args != null) {
            fragment.setArguments(args);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, fragment, fragmentName).commit();
        if (this.isModal != isModal || this.isDetailsScreen != isDetailsScreen) {
            this.isModal = isModal;
            this.isDetailsScreen = isDetailsScreen;
            invalidateOptionsMenu();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(IS_MODAL, this.isModal);
        bundle.putBoolean(IS_DETAILS_SCREEN, this.isDetailsScreen);
    }

    @NonNull
    private static FITFragment createFragment(String fragmentName) {
        FITFragment fragment;
        try {
            Object o = Class.forName(fragmentName).newInstance();
            if (!(o instanceof FITFragment)) {
                throw new ClassCastException("The fragment name should be a Fragment object: " + fragmentName);
            }
            fragment = (FITFragment) o;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Don't have access to create class " + fragmentName, e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Fragment does not have the default constructor: " + fragmentName, e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found: " + fragmentName, e);
        }
        return fragment;
    }

    public App getApp() {
        return (App) super.getApplication();
    }

    public void pushFragmentActivityForResult(FragmentInitInfo fragmentInitInfo) {
        startActivityForResult(createIntent(this, fragmentInitInfo), ACTIVITY_STATUS_REQ_CODE);
    }

    public void pushFragmentActivity(FragmentInitInfo fragmentInitInfo) {
        startActivity(createIntent(this, fragmentInitInfo));
    }

    public void popFragmentActivityWithResult(int resultCode) {
        Intent returnIntent = new Intent();
        setResult(resultCode, returnIntent);
        finish();
    }

    public void popFragmentActivity() {
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

    private FITFragment getCurrentFragment() {
        return (FITFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isModal) {
            //noinspection ConstantConditions
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getMenuInflater().inflate(R.menu.menu_modal, menu);
            return true;
        } else if (isDetailsScreen) {
            //noinspection ConstantConditions
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            return true;
        } else {
            return super.onCreateOptionsMenu(menu);
        }
    }

    @Override
    public void onBackPressed() {
        if (!isModal && !isDetailsScreen) {
            super.onBackPressed();
            return;
        }

        boolean backHandledByFragment = getCurrentFragment().onBackPressed();
        if (!backHandledByFragment) {
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
        } else if (isDetailsScreen) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    return getCurrentFragment().onBackPressed();
                default:
                    return super.onOptionsItemSelected(item);
            }
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}