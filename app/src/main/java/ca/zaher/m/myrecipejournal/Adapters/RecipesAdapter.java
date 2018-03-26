package ca.zaher.m.myrecipejournal.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import ca.zaher.m.myrecipejournal.Adapters.ViewHolders.RecipeViewHolders;
import ca.zaher.m.myrecipejournal.R;
import ca.zaher.m.myrecipejournal.data.Recipe;

/**
 * Created by zaher on 2018-02-24.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipeViewHolders> implements Filterable {
    public ArrayList<Recipe> recipes;
    private Context context;
    private final RVClickListener rvClickListener;
    RecipeFilter filter;
    public ArrayList<Recipe> filterList;

    public RecipesAdapter(ArrayList<Recipe> recipes, Context context, RVClickListener listener) {
        this.recipes = recipes;
        this.context = context;
        this.rvClickListener = listener;
        this.filterList = recipes;
    }

    @Override
    public RecipeViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item_layout, parent, false);
        return new RecipeViewHolders(view, rvClickListener);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolders holder, int position) {
        holder.bindData(recipes.get(position));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null)
            return filter = new RecipeFilter(filterList, this);
        return filter;
    }

    public void refreashData() {
        recipes.clear();
        recipes.addAll(filterList);
        notifyDataSetChanged();
    }

    public interface RVClickListener {

        void recyclerViewListClicked(int position);

        void recyclerViewListDelete(int position);
    }

}


