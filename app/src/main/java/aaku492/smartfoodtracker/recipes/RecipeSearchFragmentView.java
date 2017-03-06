package aaku492.smartfoodtracker.recipes;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import java.util.Arrays;

import aaku492.smartfoodtracker.R;
import aaku492.smartfoodtracker.common.Field;
import aaku492.smartfoodtracker.common.FunctionalUtils;
import aaku492.smartfoodtracker.common.StringUtils;
import aaku492.smartfoodtracker.common.ViewUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

import static aaku492.smartfoodtracker.common.FunctionalUtils.map;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-05.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipeSearchFragmentView extends LinearLayout {
    @BindView(R.id.recipe_search_field)
    protected Field searchField;

    @BindView(R.id.cuisine)
    protected AppCompatSpinner cuisine;

    @BindView(R.id.recipe_type)
    protected AppCompatSpinner recipeType;

    public RecipeSearchFragmentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public static RecipeSearchFragmentView inflate(LayoutInflater inflater, ViewGroup container) {
        return ViewUtils.inflate(R.layout.fragment_recipe_search, inflater, container);
    }

    public void render(final RecipeSearchQuery query) {
        searchField.clearTextChangedListeners();
        cuisine.setOnItemSelectedListener(null);
        recipeType.setOnItemSelectedListener(null);

        searchField.setText(query.getSearchQuery());
        cuisine.setSelection(query.getCuisine().ordinal());
        recipeType.setSelection(query.getRecipeType().ordinal());

        searchField.addTextChangedListener(new Field.AfterTextChangedWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                query.setSearchQuery(s.toString());
            }
        });

        cuisine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                query.setCuisine(Cuisine.values()[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        recipeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                query.setRecipeType(RecipeType.values()[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void showMessage(String message) {
        ViewUtils.showMessage(message, this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        cuisine.setAdapter(new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                map(Arrays.asList(Cuisine.values()), new FunctionalUtils.Mapper<Cuisine, String>() {
                    @Override
                    public String map(Cuisine in) {
                        return StringUtils.titleCase(in.toString());
                    }
                })
        ));
        recipeType.setAdapter(new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                map(Arrays.asList(RecipeType.values()), new FunctionalUtils.Mapper<RecipeType, String>() {
                    @Override
                    public String map(RecipeType in) {
                        return StringUtils.titleCase(in.toString());
                    }
                })
        ));
    }


}
