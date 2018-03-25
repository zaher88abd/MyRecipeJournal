package ca.zaher.m.myrecipejournal.Adapters.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import ca.zaher.m.myrecipejournal.Adapters.RecipesAdapter;
import ca.zaher.m.myrecipejournal.R;
import ca.zaher.m.myrecipejournal.data.Recipe;

/**
 * Created by zaher on 2018-02-24.
 */

public class RecipeViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView tvRecipeName;
    private RatingBar ratingBar;
    private RecipesAdapter.RVClickListener mlListener;
    private ImageView imageView;

    public RecipeViewHolders(View itemView, RecipesAdapter.RVClickListener listener) {
        super(itemView);
        mlListener = listener;
        tvRecipeName = itemView.findViewById(R.id.tv_recipe_name);
        ratingBar = itemView.findViewById(R.id.rb_recipe_rate);
        imageView = itemView.findViewById(R.id.img_delete);
    }

    public void bindData(Recipe recipe) {
        tvRecipeName.setText(recipe.getName());
        ratingBar.setRating(recipe.rating);
        itemView.setOnClickListener(this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlListener.recyclerViewListDelete(getLayoutPosition());
            }
        });
    }

    @Override
    public void onClick(View v) {
        mlListener.recyclerViewListClicked(getLayoutPosition());
    }
}
