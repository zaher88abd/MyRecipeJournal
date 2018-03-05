package Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import Adapters.ViewHolders.RecipeViewHolders;
import ca.zaher.m.myrecipejournal.R;
import data.Recipe;

/**
 * Created by zaher on 2018-02-24.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipeViewHolders> {
    List<Recipe> recipes;
    Context context;

    public RecipesAdapter(List<Recipe> recipes, Context context) {
        this.recipes = recipes;
        this.context = context;
    }

    @Override
    public RecipeViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item_layout, parent, false);
        return new RecipeViewHolders(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolders holder, int position) {
        ((RecipeViewHolders) holder).bindData(recipes.get(position));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}
//https://willowtreeapps.com/ideas/android-fundamentals-working-with-the-recyclerview-adapter-and-viewholder-pattern/