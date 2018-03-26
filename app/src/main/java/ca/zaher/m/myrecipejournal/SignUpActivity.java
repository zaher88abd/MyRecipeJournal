package ca.zaher.m.myrecipejournal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import ca.zaher.m.myrecipejournal.Activities.MainActivity;

/**
 * Sign-up user interface
 * Created by zaher on 2018-02-21.
 */

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etCPassword;
    private ProgressDialog progressDialog;
    private Button btnSignUp;
    private Button btnSignIn;
    private Button btnSignUpShow;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_in_user);
        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etCPassword = findViewById(R.id.et_password_confirm);
        progressDialog = new ProgressDialog(this);
        btnSignUp = findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
        btnSignIn = findViewById(R.id.btn_signin);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        btnSignUpShow = findViewById(R.id.btn_signup_show);
        btnSignUpShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSignUp.setVisibility(View.VISIBLE);
                btnSignUpShow.setVisibility(View.GONE);
                etCPassword.setVisibility(View.VISIBLE);
                btnSignIn.setVisibility(View.GONE);
            }
        });
    }

    private void signIn() {
        final String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            if (password.length() < 6) {
                Toast.makeText(this, R.string.password_length_message, Toast.LENGTH_SHORT).show();
                return;
            }
            progressDialog.setMessage(getString(R.string.sign_in_wait));
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Toast.makeText(SignUpActivity.this, "Welcome, " + email.split("@")[0], Toast.LENGTH_SHORT).show();
                    backToMainActivity();
                }
            });
        }
    }

    public void signUp() {
        final String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String cPassword = etCPassword.getText().toString();
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(cPassword)) {
            if (password.length() < 6) {
                Toast.makeText(this, R.string.password_length_message, Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(cPassword)) {
                Toast.makeText(this, R.string.password_not_match, Toast.LENGTH_LONG).show();
                return;
            }
            progressDialog.setMessage(getString(R.string.signup_waite));
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.cancel();
                    if (task.isSuccessful()) {
                        FirebaseUser user = task.getResult().getUser();
                        DatabaseReference userDbRef = FirebaseDatabase.getInstance()
                                .getReference().child("user").child(user.getUid());
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("email", email);
                        userDbRef.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                } else {
                                    //user.delete();
                                    Toast.makeText(SignUpActivity.this,
                                            "Could not add the user to the database", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(SignUpActivity.this,
                                "Could not add the user", Toast.LENGTH_SHORT).show();
                        Log.e("Firebase Error", task.getException().toString());
                    }
                }
            });
        } else {
            Toast.makeText(this, R.string.error_signup, Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Welcome, " + email.split("@")[0], Toast.LENGTH_SHORT).show();
        backToMainActivity();
    }

    private void backToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
//Referance
