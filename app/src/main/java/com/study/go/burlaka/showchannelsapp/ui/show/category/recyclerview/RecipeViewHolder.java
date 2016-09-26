package com.study.go.burlaka.showchannelsapp.ui.show.category.recyclerview;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.study.go.burlaka.showchannelsapp.R;

/**
 * Created by Operator on 25.09.2016.
 */
public class RecipeViewHolder extends ParentViewHolder {

    private TextView mRecipeTextView;

    public RecipeViewHolder(View itemView) {
        super(itemView);
        mRecipeTextView = (TextView) itemView.findViewById(R.id.recipe_textview);
    }

    public void bind(Recipe recipe) {
        mRecipeTextView.setText(recipe.getName());
    }
}