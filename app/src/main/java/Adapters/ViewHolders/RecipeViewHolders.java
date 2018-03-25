package Adapters.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ca.zaher.m.myrecipejournal.R;
import data.Recipe;

/**
 * Created by zaher on 2018-02-24.
 */

public class RecipeViewHolders extends RecyclerView.ViewHolder {
    private TextView tvRecipeName;

    public RecipeViewHolders(View itemView) {
        super(itemView);
        tvRecipeName = itemView.findViewById(R.id.tv_recipe_name);
    }

    public void bindData(Recipe recipe) {
        tvRecipeName.setText(recipe.getName());
    }
}
