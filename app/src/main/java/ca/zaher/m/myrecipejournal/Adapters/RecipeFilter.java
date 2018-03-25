package ca.zaher.m.myrecipejournal.Adapters;

/**
 * Created by zaher on 2018-03-17.
 */


import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import ca.zaher.m.myrecipejournal.data.Recipe;

public class RecipeFilter extends Filter {
    ArrayList<Recipe> arrayList;
    RecipesAdapter adapter;

    public RecipeFilter(ArrayList<Recipe> arrayList, RecipesAdapter adapter) {
        this.arrayList = arrayList;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        constraint = constraint.toString().toLowerCase();
        FilterResults results = new FilterResults();
        if (!constraint.toString().isEmpty()) {
            ArrayList<Recipe> recipesFounded = new ArrayList<>();
            for (Recipe recipe : arrayList) {
                if (recipe.name.toLowerCase().contains(constraint))
                    recipesFounded.add(recipe);
            }
            results.values = recipesFounded;
            results.count = recipesFounded.size();
        } else {
            results.values = arrayList;
            results.count = arrayList.size();
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.recipes = (ArrayList<Recipe>) results.values;
        adapter.notifyDataSetChanged();
    }

}
