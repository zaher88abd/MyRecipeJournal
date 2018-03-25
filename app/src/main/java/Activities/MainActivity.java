package Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import Adapters.RecipesAdapter;
import ca.zaher.m.myrecipejournal.R;
import data.ListRecipes;
import data.Recipe;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyRecipeTag";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private Toolbar toolbar;
    private FloatingActionButton faBtn;
    private RecyclerView rvRecipes;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userDataRef;
    private DatabaseReference userRecipeRef;
    private RecipesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        toolbar = findViewById(R.id.toolBar);
        faBtn = findViewById(R.id.addRecipe);
        rvRecipes = findViewById(R.id.rv_recipes_list);
        firebaseDatabase = FirebaseDatabase.getInstance();
        final ArrayList<Recipe> recipeArrayList = new ArrayList<>();
        setSupportActionBar(toolbar);
        if (user != null) {
            setTitle(user.getEmail().split("@")[0]);
        }
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //check the user status.
                if (mAuth.getCurrentUser() != null) {
                    userDataRef = firebaseDatabase.getReference().child(getString(R.string.user_ref)).child(mAuth.getCurrentUser().getUid());
                    userRecipeRef = userDataRef.child(getString(R.string.recipe_ref));
                    String r = userRecipeRef.toString();
                    Log.e(TAG, r);

                    userRecipeRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot child :
                                    dataSnapshot.getChildren()) {
                                Recipe recipe = child.getValue(Recipe.class);
                                recipeArrayList.add(recipe);
                            }
                            adapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    Toast.makeText(MainActivity.this, userDataRef.toString(), Toast.LENGTH_LONG).show();
                }
            }
        };

        adapter = new RecipesAdapter(recipeArrayList, MainActivity.this);
        rvRecipes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvRecipes.setAdapter(adapter);
        faBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        if (user != null) {
            MenuItem signIn = menu.findItem(R.id.mi_signIn);
            MenuItem signOut = menu.findItem(R.id.mi_signOut);
            signIn.setVisible(false);
            signOut.setVisible(true);
        } else {
            MenuItem signIn = menu.findItem(R.id.mi_signIn);
            MenuItem signOut = menu.findItem(R.id.mi_signOut);
            signIn.setVisible(true);
            signOut.setVisible(false);
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            mAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mi_signOut && user != null) {
            FirebaseAuth.getInstance().signOut();
            user = mAuth.getCurrentUser();
            Toast.makeText(this, "SignOut", Toast.LENGTH_SHORT).show();
            setTitle("");
        }
        if (item.getItemId() == R.id.mi_signIn && user == null) {
            if (user != null)
                Toast.makeText(MainActivity.this, user.getEmail(), Toast.LENGTH_SHORT).show();
            else {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        }
        invalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }


    private void updateUI(FirebaseUser currentUser) {
        if (currentUser == null) {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }
}
//https://stackoverflow.com/questions/9570237/android-check-internet-connection
//https://stackoverflow.com/questions/10692755/how-do-i-hide-a-menu-item-in-the-actionbar