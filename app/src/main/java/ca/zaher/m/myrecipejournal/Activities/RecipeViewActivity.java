package ca.zaher.m.myrecipejournal.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.zaher.m.myrecipejournal.R;
import ca.zaher.m.myrecipejournal.data.Ingredient;
import ca.zaher.m.myrecipejournal.data.Recipe;

/**
 * Created by zaher on 2018-03-15.
 */

public class RecipeViewActivity extends AppCompatActivity {
    DatabaseReference firebaseDatabase;
    Recipe recipe;
    boolean setOnEdit = false;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        recipe = (Recipe) intent.getSerializableExtra("recipe");
        EditText tvTemp = findViewById(R.id.tv_recipe_name);
        tvTemp.setText(recipe.getName());
        tvTemp = findViewById(R.id.tv_desc);
        tvTemp.setText(recipe.description);
        RatingBar rb = findViewById(R.id.rb_recipe_rate);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                recipe.rating = rating;
            }
        });
        setOnEdit = false;
        rb.setRating(recipe.rating);
        tvTemp = findViewById(R.id.tv_ingredient);
        String temp = "";
        for (Ingredient ingredient : recipe.getIngredientsList()) {
            temp += String.format("%-8s %-5s %-8s\n", ingredient.getMaterial().toString()
                    , ingredient.quantity
                    , ingredient.measurement);
        }
        tvTemp.setText(temp);
        tvTemp = findViewById(R.id.tv_instruction);
        tvTemp.setText(recipe.getInstructions());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edite_menu, menu);
        if (!setOnEdit) {
            MenuItem edit = menu.findItem(R.id.mi_edit);
            MenuItem save = menu.findItem(R.id.mi_save);
            edit.setVisible(true);
            save.setVisible(false);
            setOnEdit = true;
        } else {
            MenuItem edit = menu.findItem(R.id.mi_edit);
            MenuItem save = menu.findItem(R.id.mi_save);
            edit.setVisible(false);
            save.setVisible(true);
            setOnEdit = false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mi_edit)
            editLayout();
        if (item.getItemId() == R.id.mi_save)
            saveChange();
        invalidateOptionsMenu();
        return super.onOptionsItemSelected(item);

    }

    private void saveChange() {
        EditText etName = findViewById(R.id.tv_recipe_name);
        recipe.setName(etName.getText().toString());
        EditText etDescription = findViewById(R.id.tv_desc);
        recipe.setDescription(etDescription.getText().toString());
        EditText etInstruction = findViewById(R.id.tv_instruction);
        recipe.setInstructions(etInstruction.getText().toString());
        EditText etIngredients = findViewById(R.id.tv_ingredient);
        String ingredients = etIngredients.getText().toString();
        for (String ingredientString :
                ingredients.split("\n")) {
            String ss = ingredientString.split("( +)")[1];
            double quantity = Double.parseDouble(ss.trim());
            Ingredient ingredient = new Ingredient(ingredientString.split("( +)")[0]
                    , quantity, ingredientString.split("( +)")[2]);
            recipe.addIngredients(ingredient);
        }
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance()
                .getReference().child("user").child(userId).child("recipe").child(recipe.uid);
        firebaseDatabase.setValue(recipe);
        firebaseDatabase.keepSynced(true);
        Toast.makeText(this, R.string.done_save_recipe, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RecipeViewActivity.this, MainActivity.class));
    }

    private void editLayout() {
        EditText tvTemp = findViewById(R.id.tv_recipe_name);
        tvTemp.setEnabled(true);
        tvTemp = findViewById(R.id.tv_desc);
        tvTemp.setEnabled(true);
        tvTemp = findViewById(R.id.tv_ingredient);
        tvTemp.setEnabled(true);
        tvTemp = findViewById(R.id.tv_instruction);
        tvTemp.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance()
                .getReference().child("user").child(userId).child("recipe").child(recipe.uid);
        firebaseDatabase.setValue(recipe);
        firebaseDatabase.keepSynced(true);
    }
}
