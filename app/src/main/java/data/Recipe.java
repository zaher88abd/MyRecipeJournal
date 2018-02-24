package data;

import java.util.ArrayList;
import java.util.List;

/**
 * the class of the Recipe data
 * Created by zaher on 2018-02-22.
 */

public class Recipe {
    private String name;
    private String description;
    private List<Ingredient> ingredientsList;
    private String instructions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Ingredient> getIngredientsList() {
        return ingredientsList;
    }

    public void addIngredients(Ingredient ingredient) {
        this.ingredientsList.add(ingredient);
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Recipe(String name, String description, List<Ingredient> ingredientsList, String instructions) {
        this.name = name;
        this.description = description;
        this.ingredientsList = ingredientsList;
        this.instructions = instructions;
    }

    public Recipe() {
        this.ingredientsList = new ArrayList<>();
    }

}
