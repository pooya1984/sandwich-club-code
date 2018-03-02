package com.udacity.sandwichclub.utils;


import android.support.annotation.NonNull;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String LOG_TAG = JsonUtils.class.getName();
    public static Sandwich parseSandwichJson(String json) {
        try {
            return parseSandwich(json);
        } catch (JSONException e) {
            return null;
        }   }
    private static Sandwich parseSandwich(String jsonString) throws JSONException {
        Sandwich sandwich = new Sandwich();
        JSONObject json = new JSONObject(jsonString);

        JSONObject name = json.getJSONObject("name");
        sandwich.setMainName(name.getString("mainName"));
        sandwich.setAlsoKnownAs(extractArray(name, "alsoKnownAs"));

        sandwich.setPlaceOfOrigin(json.getString("placeOfOrigin"));
        sandwich.setDescription(json.getString("description"));
        sandwich.setImage(json.getString("image"));
        sandwich.setIngredients(extractArray(json, "ingredients"));
        return sandwich;
    }
    @NonNull
    private static List<String> extractArray(JSONObject json, String name) throws JSONException {
        List<String> elements = new ArrayList<>();
        JSONArray alsoKnownAs = json.getJSONArray(name);
        for (int i = 0; i < alsoKnownAs.length(); i++) {
            elements.add(alsoKnownAs.getString(i));
        }
        return elements;
    }
}
