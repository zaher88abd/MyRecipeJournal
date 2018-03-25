package ca.zaher.m.myrecipejournal.data;

import java.util.ArrayList;

/**
 * Created by zaher on 2018-02-26.
 */

public class ListRecipes {
    private ArrayList<Recipe> recipes;

    public ListRecipes() {
        recipes=new ArrayList<>();
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }
}
