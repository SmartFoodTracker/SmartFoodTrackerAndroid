package aaku492.smartfoodtracker.recipes;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import java.util.Arrays;

import aaku492.smartfoodtracker.R;
import aaku492.smartfoodtracker.common.Chip;
import aaku492.smartfoodtracker.common.Field;
import aaku492.smartfoodtracker.common.FunctionalUtils;
import aaku492.smartfoodtracker.common.StringUtils;
import aaku492.smartfoodtracker.common.ViewUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

import static aaku492.smartfoodtracker.common.FunctionalUtils.indexOf;
import static aaku492.smartfoodtracker.common.FunctionalUtils.map;
import static aaku492.smartfoodtracker.common.StringUtils.titleCase;

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

    @BindView(R.id.intolerances_container)
    protected ViewGroup intolerancesContainer;

    @BindView(R.id.ingredients_container)
    protected ViewGroup ingredientsContainer;

    @BindView(R.id.ingredients_field)
    protected Field ingredientsField;
    private RecipeSearchQuery query;

    public RecipeSearchFragmentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public static RecipeSearchFragmentView inflate(LayoutInflater inflater, ViewGroup container) {
        return ViewUtils.inflate(R.layout.fragment_recipe_search, inflater, container);
    }

    public void render(final RecipeSearchQuery query) {
        this.query = query;
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

        for (int i = 0; i < intolerancesContainer.getChildCount(); ++i) {
            final Chip child = (Chip) intolerancesContainer.getChildAt(i);
            child.setOnSelectionChangedListener(null);
            child.setSelected(query.getIntolerances().isAdded(Intolerances.Intolerance.values()[i]));
            final int finalI = i;
            child.setOnSelectionChangedListener(new Chip.OnSelectionChangedListener() {
                @Override
                public void onSelectionChanged(Chip v, boolean selected) {
                    if (selected) {
                        query.getIntolerances().add(Intolerances.Intolerance.values()[finalI]);
                    } else {
                        query.getIntolerances().remove(Intolerances.Intolerance.values()[finalI]);
                    }
                }
            });
        }

        ingredientsContainer.removeAllViews();
        for (String ingredient : query.getIngredients().getList()) {
            addIngredientChip(ingredient);
        }

        ingredientsField.setOnEditorActionListener(new Field.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(Field field, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String ingredient = field.getText().trim();
                    if (!ingredient.equals("")) {
                        addIngredient(ingredient, true);
                    }
                    ingredientsField.setText("");
                    return true;
                }
                return false;
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
                        return titleCase(in.toString());
                    }
                })
        ));
        recipeType.setAdapter(new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                map(Arrays.asList(RecipeType.values()), new FunctionalUtils.Mapper<RecipeType, String>() {
                    @Override
                    public String map(RecipeType in) {
                        return titleCase(in.toString());
                    }
                })
        ));

        for (Intolerances.Intolerance intolerance : Intolerances.Intolerance.values()) {
            intolerancesContainer.addView(createIntoleranceChip(intolerance));
        }
    }

    private void addIngredient(String ingredient, boolean uiFeedback) {
        ingredient = ingredient.trim();
        for (int i = 0; i < ingredientsContainer.getChildCount(); ++i) {
            if (((Chip) ingredientsContainer.getChildAt(i)).getText().toString().toLowerCase().equals(ingredient.toLowerCase())) {
                if (uiFeedback) {
                    ViewUtils.bounceAnimation(ingredientsContainer.getChildAt(i));
                }
                return;
            }
        }
        query.addIngredients(ingredient);
        addIngredientChip(ingredient);
    }

    private Chip createIntoleranceChip(Intolerances.Intolerance intolerance) {
        Chip chip = new Chip(getContext());
        chip.setLayoutParams(new Chip.LayoutParams(Chip.LayoutParams.WRAP_CONTENT, Chip.LayoutParams.WRAP_CONTENT));
        chip.setText(titleCase(intolerance.toString()));
        return chip;
    }

    private void addIngredientChip(String ingredient) {
        Chip chip = new Chip(getContext());
        chip.setLayoutParams(new Chip.LayoutParams(Chip.LayoutParams.WRAP_CONTENT, Chip.LayoutParams.WRAP_CONTENT));
        chip.setText(titleCase(ingredient.trim()));
        chip.setSelected(true);
        ingredientsContainer.addView(chip);
        chip.setOnSelectionChangedListener(new Chip.OnSelectionChangedListener() {
            @Override
            public void onSelectionChanged(final Chip v, boolean selected) {
                if (selected) {
                    throw new IllegalStateException("Chip couldn't have been selected, because it's removed upon unselected.");
                }
                ingredientsContainer.removeView(v);
                int i = indexOf(query.getIngredients().getList(), new FunctionalUtils.Predicate<String>() {
                    @Override
                    public boolean test(String in) {
                        return in.toLowerCase().equals(v.getText().toString().toLowerCase());
                    }
                });
                query.getIngredients().getList().remove(i);
            }
        });
    }

    public void onAcceptPressed() {
        String ingredient = StringUtils.titleCase(ingredientsField.getText());
        ingredientsField.setText("");
        if (ingredient.length() == 0) {
            return;
        }
        addIngredient(ingredient, false);
    }
}
