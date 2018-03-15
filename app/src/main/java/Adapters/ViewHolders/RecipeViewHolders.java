package Adapters.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import Adapters.RecipesAdapter;
import ca.zaher.m.myrecipejournal.R;
import data.Recipe;

/**
 * Created by zaher on 2018-02-24.
 */

public class RecipeViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView tvRecipeName;
    private RatingBar ratingBar;
    private RecipesAdapter.RVClickListener mlListener;

    public RecipeViewHolders(View itemView, RecipesAdapter.RVClickListener listener) {
        super(itemView);
        mlListener = listener;
        tvRecipeName = itemView.findViewById(R.id.tv_recipe_name);
        ratingBar = itemView.findViewById(R.id.rb_recipe_rate);
    }

    public void bindData(Recipe recipe) {
        tvRecipeName.setText(recipe.getName());
        ratingBar.setRating(recipe.rating);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mlListener.recyclerViewListClicked(getLayoutPosition());
    }
}
