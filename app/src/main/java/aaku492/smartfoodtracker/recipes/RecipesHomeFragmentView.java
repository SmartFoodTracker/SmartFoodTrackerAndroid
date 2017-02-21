package aaku492.smartfoodtracker.recipes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import aaku492.smartfoodtracker.R;
import aaku492.smartfoodtracker.common.ViewUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-02-20.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class RecipesHomeFragmentView extends RelativeLayout {
    @BindView(R.id.recipes_card_container)
    protected RecyclerView recipesCardContainer;

    public RecipesHomeFragmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static RecipesHomeFragmentView inflate(LayoutInflater inflater, ViewGroup container) {
        return ViewUtils.inflate(R.layout.fragment_recipes_home, inflater, container);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        recipesCardContainer.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    public void render(RecipesHomeAdapter adapter) {
        recipesCardContainer.setAdapter(adapter);
        recipesCardContainer.addItemDecoration(new RecipesHomeAdapter.SpacesItemDecoration((int) getContext().getResources().getDimension(R.dimen.recipe_card_margin)));
    }
}