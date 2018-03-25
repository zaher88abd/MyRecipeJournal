package ca.zaher.m.myrecipejournal.data;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zaher on 2018-02-25.
 */

public class User {
    public String uid;
    public String email;
    public HashMap<String, Recipe> recipes;

    public User() {
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("email", email);
        result.put("recipes", recipes);
        return result;
    }
}
