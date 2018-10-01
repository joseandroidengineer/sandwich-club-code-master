package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        JSONObject jsonObject = null;

        try{
            jsonObject = new JSONObject(json);

        }catch (JSONException e){
            e.printStackTrace();
        }

        return getSandwich(jsonObject);
    }

    private static JSONObject getJsonNameObject(JSONObject jsonObject){
        JSONObject jsonNameObject = null;
        try {
            jsonNameObject = jsonObject.getJSONObject("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonNameObject;
    }

    private static String getJsonMainName(JSONObject jsonNameObject){
        String mainName = null;
        try {
            mainName = jsonNameObject.getString("mainName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainName;
    }
    private static List<String> getJsonAlsoKnownAs(JSONObject jsonNameObject){
        JSONArray alsoKnownAs = null;
        try {
            alsoKnownAs = jsonNameObject.getJSONArray("alsoKnownAs");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<String> alsoKnownAsArrayList = (ArrayList<String>) getListFromJsonArray(alsoKnownAs);
        return alsoKnownAsArrayList;
    }

    private static String getPlaceOfOriginJson(JSONObject jsonObject){
        String placeOfOrigin = null;
        try {
            placeOfOrigin = jsonObject.getString("placeOfOrigin");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return placeOfOrigin;
    }

    private static String getDescriptionJson(JSONObject jsonObject){
        String description = null;
        try {
            description = jsonObject.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return description;
    }

    private static String getImageJson(JSONObject jsonObject){
        String image = null;
        try {
            image = jsonObject.getString("image");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return image;
    }

    private static List<String> getIngredientsJson(JSONObject jsonObject){
        JSONArray ingredients = null;
        try {
            ingredients = jsonObject.getJSONArray("ingredients");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<String> ingredientsArrayList = (ArrayList<String>) getListFromJsonArray(ingredients);
        return ingredientsArrayList;
    }

    private static List<String> getListFromJsonArray(JSONArray jsonArray){
        ArrayList<String> listOfStrings = new ArrayList<>();
        List<String> emptyList = new ArrayList<>();
        if (jsonArray != null) {
            for (int i=0;i<jsonArray.length();i++){
                try {
                    listOfStrings.add(jsonArray.getString(i));
                    Log.e("JSONUTILS","So far so good AGAIN");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return listOfStrings;
        }else{
            return emptyList;
        }


    }

    private static Sandwich getSandwich(JSONObject jsonObject){
        JSONObject jsonNameObject = getJsonNameObject(jsonObject);
        Sandwich sandwich = new Sandwich();
        /**Getting strings from nameObject**/
        sandwich.setAlsoKnownAs(getJsonAlsoKnownAs(jsonNameObject));
        sandwich.setMainName(getJsonMainName(jsonNameObject));
        /**Getting Lists and strings from main JSON Object**/
        sandwich.setPlaceOfOrigin(getPlaceOfOriginJson(jsonObject));
        sandwich.setDescription(getDescriptionJson(jsonObject));
        sandwich.setImage(getImageJson(jsonObject));
        sandwich.setIngredients(getIngredientsJson(jsonObject));
        return sandwich;

    }
}
