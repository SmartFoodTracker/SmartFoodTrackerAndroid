package aaku492.smartfoodtracker.recipes;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import aaku492.smartfoodtracker.R;
import aaku492.smartfoodtracker.common.ViewUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-02-21.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipeDetailFragmentView extends ScrollView {
    private static final String LOG_TAG = RecipeDetailFragmentView.class.getName();
    @BindView(R.id.recipe_image)
    protected ImageView recipeImage;

    @BindView(R.id.recipe_title)
    protected TextView recipeTitle;

    @BindView(R.id.recipe_steps)
    protected TextView recipeSteps;

    @BindView(R.id.recipe_steps_label)
    protected TextView recipeStepsLabel;

    @BindView(R.id.recipe_ingredients)
    protected TextView recipeIngredients;

    @BindView(R.id.recipe_source_container)
    protected View recipeSourceContainer;

    @BindView(R.id.recipe_source)
    protected TextView recipeSource;

    public RecipeDetailFragmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static RecipeDetailFragmentView inflate(LayoutInflater inflater, ViewGroup container) {
        return ViewUtils.inflate(R.layout.fragment_recipe_detail, inflater, container);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void render(RecipeResponse.Recipe recipe) {
        recipeTitle.setText(recipe.getTitle());

        String ingredients = buildIngredientsList(recipe.getSatisfiedIngredients(), recipe.getUnsatisfiedIngredients());
        if (ingredients.equals("")) {
            recipeIngredients.setText("");
            recipeIngredients.setVisibility(GONE);
        } else {
            recipeIngredients.setText(ingredients);
            recipeIngredients.setVisibility(VISIBLE);
        }

        String steps = buildNumberedSteps(recipe.getSteps());
        if (steps.equals("")) {
            recipeSteps.setText("");
            recipeSteps.setVisibility(GONE);
            recipeStepsLabel.setVisibility(GONE);
        } else {
            recipeSteps.setText(steps);
            recipeSteps.setVisibility(VISIBLE);
            recipeStepsLabel.setVisibility(VISIBLE);
        }

        if (recipe.getSourceUrl() == null || recipe.getSourceUrl().isEmpty()) {
            recipeSource.setText("");
            recipeSourceContainer.setVisibility(GONE);
        } else {
            try {
                recipeSource.setText(buildSourceText(recipe.getSourceUrl()));
                recipeSource.setMovementMethod(LinkMovementMethod.getInstance());
                recipeSourceContainer.setVisibility(VISIBLE);
            } catch (MalformedURLException e) {
                recipeSource.setText("");
                recipeSourceContainer.setVisibility(GONE);
                Log.e(LOG_TAG, "Malformed recipe source URL: " + recipe.getSourceUrl());
            }
        }

        if (recipe.getImageUrl() == null || recipe.getImageUrl().isEmpty()) {
            recipeImage.setVisibility(GONE);
        } else {
            recipeImage.setVisibility(VISIBLE);
            Glide.with(getContext())
                    .load(recipe.getImageUrl())
                    .centerCrop()
                    .placeholder(R.drawable.ic_recipes)
                    .into(recipeImage);
        }
    }

    @NonNull
    private String buildIngredientsList(@Nullable List<String> satisfiedIngredients, @Nullable List<String> unsatisfiedIngredients) {
        if ((satisfiedIngredients == null || satisfiedIngredients.isEmpty()) &&
                (unsatisfiedIngredients == null || unsatisfiedIngredients.isEmpty())) {
            return "";
        }

        satisfiedIngredients = satisfiedIngredients == null ? new ArrayList<String>() : satisfiedIngredients;
        unsatisfiedIngredients = unsatisfiedIngredients == null ? new ArrayList<String>() : unsatisfiedIngredients;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < satisfiedIngredients.size(); ++i) {
            sb.append(getContext().getString(R.string.recipe_satisfied_ingredient_formatter, satisfiedIngredients.get(i)));
            if (i < satisfiedIngredients.size() - 1 || !unsatisfiedIngredients.isEmpty()) {
                sb.append("\n");
            }
        }
        for (int i = 0; i < unsatisfiedIngredients.size(); ++i) {
            sb.append(getContext().getString(R.string.recipe_unsatisfied_ingredient_formatter, unsatisfiedIngredients.get(i)));
            if (i < unsatisfiedIngredients.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @NonNull
    private Spanned buildSourceText(String sourceUrl) throws MalformedURLException {
        String linkText = getContext().getString(R.string.recipe_source_url_html_formatter, new URL(sourceUrl).getHost(), sourceUrl);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(linkText, Html.FROM_HTML_MODE_LEGACY);
        } else {
            //noinspection deprecation
            return Html.fromHtml(linkText);
        }
    }

    @NonNull
    private String buildNumberedSteps(@Nullable List<String> steps) {
        if (steps == null || steps.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < steps.size(); ++i) {
            sb.append(getContext().getString(R.string.recipe_step_formatter, i + 1, steps.get(i)));
            if (i < steps.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
