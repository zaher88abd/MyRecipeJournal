package Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.zaher.m.myrecipejournal.R;
import data.Recipe;

/**
 * Created by zaher on 2018-03-15.
 */

public class RecipeViewActivity extends AppCompatActivity {
    DatabaseReference firebaseDatabase;
    Recipe recipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        Intent intent = getIntent();
        recipe = (Recipe) intent.getSerializableExtra("recipe");
        TextView tvTemp = findViewById(R.id.tv_recipe_name);
        tvTemp.setText(recipe.getName());
        tvTemp = findViewById(R.id.tv_desc);
        tvTemp.setText(recipe.description);
        RatingBar rb = findViewById(R.id.rb_recipe_rate);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                recipe.rating=rating;
            }
        });
        rb.setRating(recipe.rating);
        tvTemp = findViewById(R.id.tv_ingredient);
        tvTemp.setText(recipe.getInstructions());
        tvTemp = findViewById(R.id.tv_instruction);
        tvTemp.setText(recipe.getInstructions());
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
//    https://stackoverflow.com/questions/36185620/how-to-remove-child-nodes-in-firebase-android
}
