package ca.zaher.m.myrecipejournal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import data.Ingredient;
import data.Recipe;

/**
 * Created by zaher on 2018-02-22.
 */

public class AddRecipeActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etDescription;
    private EditText etIngredients;
    private EditText etInstruction;
    Recipe recipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_recipe);
        String[] measurement = getResources().getStringArray(R.array.measurement);
        Spinner spIngredientMeasurement = findViewById(R.id.sp_ingredient_measurement);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, measurement);
        spIngredientMeasurement.setAdapter(adapter);
        recipe = new Recipe();
    }

    public void addIngredient(View view) {
        EditText etIngredientMaterial = findViewById(R.id.et_ingredient_material);
        EditText etIngredientQuantity = findViewById(R.id.et_ingredient_quantity);
        Spinner spIngredientMeasurement = findViewById(R.id.sp_ingredient_measurement);
    }
}
//http://www.worldbestlearningcenter.com/tips/Android-add-string-array-in-xml-file-to-spinner.htm