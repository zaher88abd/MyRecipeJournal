package ca.zaher.m.myrecipejournal;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        if (user != null) {
            setTitle(user.getEmail().split("@")[0]);
        }
        findViewById(R.id.textTV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null)
                    Toast.makeText(MainActivity.this, user.getEmail(), Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(MainActivity.this, ActivitySignUp.class);
                    startActivity(intent);
                }
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
                Intent intent = new Intent(MainActivity.this, ActivitySignUp.class);
                startActivity(intent);
            }
        }
        invalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }


    private void updateUI(FirebaseUser currentUser) {
        if (currentUser == null) {
            Intent intent = new Intent(this, ActivitySignUp.class);
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