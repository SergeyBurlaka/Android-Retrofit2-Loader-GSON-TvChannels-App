package com.study.go.burlaka.showchannelsapp.ui.category.recyclerview;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.study.go.burlaka.showchannelsapp.R;

/**
 * Created by Operator on 25.09.2016.
 */
public class IngredientViewHolder extends ChildViewHolder {

    private TextView mIngredientTextView;

    public IngredientViewHolder(View itemView) {
        super(itemView);
        mIngredientTextView = (TextView) itemView.findViewById(R.id.ingredient_textview);
    }


    public void bind(Channels channels) {
        mIngredientTextView.setText(channels.getName());
    }
}
