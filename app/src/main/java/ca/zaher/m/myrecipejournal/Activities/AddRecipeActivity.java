package ca.zaher.m.myrecipejournal.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.zaher.m.myrecipejournal.R;
import ca.zaher.m.myrecipejournal.data.Ingredient;
import ca.zaher.m.myrecipejournal.data.Recipe;
import ca.zaher.m.myrecipejournal.data.User;

/**
 * Created by zaher on 2018-02-22.
 */

public class AddRecipeActivity extends AppCompatActivity {
    private static final String TAG = "MyRecipeTag";
    private EditText etName;
    private EditText etDescription;
    private EditText etIngredients;
    private EditText etInstruction;
    Recipe recipe;
    Button btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_recipe);
        String[] measurement = getResources().getStringArray(R.array.measurement);
        Spinner spIngredientMeasurement = findViewById(R.id.sp_ingredient_measurement);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, measurement);
        spIngredientMeasurement.setAdapter(adapter);
        recipe = new Recipe();
        etName = findViewById(R.id.et_name);
        etDescription = findViewById(R.id.et_description);
        etIngredients = findViewById(R.id.et_ingredients);
        etInstruction = findViewById(R.id.et_instruction);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRecipe();
            }
        });
        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            String receivedAction = receivedIntent.getAction();
            if (receivedAction != null && receivedAction.equals(Intent.ACTION_SEND)) {
                String receivedText = receivedIntent.getStringExtra(Intent.EXTRA_TEXT);
                etDescription.setText(receivedText);
                Log.d(TAG, receivedText);
            }
        }
    }

    public void addIngredient(View view) { // Add new Ingredient to the Edit text
        EditText etIngredientMaterial = findViewById(R.id.et_ingredient_material);
        EditText etIngredientQuantity = findViewById(R.id.et_ingredient_quantity);
        Spinner spIngredientMeasurement = findViewById(R.id.sp_ingredient_measurement);
        String measurement = spIngredientMeasurement.getSelectedItem().toString();
        String ingredient = String.format("%-8s %-5s %-8s\n", etIngredientMaterial.getText().toString()
                , etIngredientQuantity.getText().toString()
                , measurement);
        ingredient += etIngredients.getText();
        etIngredients.setText(ingredient);
        emptyIngredient();
    }

    public void emptyIngredient() {
        EditText etIngredientMaterial = findViewById(R.id.et_ingredient_material);
        EditText etIngredientQuantity = findViewById(R.id.et_ingredient_quantity);
        etIngredientMaterial.setText("");
        etIngredientQuantity.setText("");
    }

    public void saveRecipe() {
        recipe.setName(etName.getText().toString());
        recipe.setDescription(etDescription.getText().toString());
        recipe.setInstructions(etInstruction.getText().toString());
        String ingredients = etIngredients.getText().toString();
        for (String ingredientString :
                ingredients.split("\n")) {
            String ss = ingredientString.split("( +)")[1];
            double quantity = Double.parseDouble(ss.trim());
            Ingredient ingredient = new Ingredient(ingredientString.split("( +)")[0]
                    , quantity, ingredientString.split("( +)")[2]);
            recipe.addIngredients(ingredient);
        }
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference userRecipes = firebaseDatabase.getReference()
                .child(getString(R.string.user_ref))
                .child(userId).child(getString(R.string.recipe_ref));
        userRecipes.keepSynced(true);
        String uid = userRecipes.getKey();
        DatabaseReference newRecipe = userRecipes.push();
        recipe.uid = uid;
        newRecipe.setValue(recipe);
        Toast.makeText(this, R.string.done_save_recipe, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(AddRecipeActivity.this, MainActivity.class));
//
//        Bundle extras = getIntent().getExtras();
//        String value1 = extras.getString(Intent.EXTRA_TEXT);
//        Log.d(TAG, value1);


    }


}
