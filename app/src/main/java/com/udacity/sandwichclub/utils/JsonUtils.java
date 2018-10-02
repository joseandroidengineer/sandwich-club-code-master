package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String NAME_KEY = "name";
    private static final String MAIN_NAME_KEY = "mainName";
    private static final String ALSO_KNOWN_AS_KEY = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN_KEY = "placeOfOrigin";
    private static final String DESCRIPTION_KEY = "description";
    private static final String IMAGE_KEY = "image";
    private static final String INGREDIENTS_KEY = "ingredients";



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
        return jsonObject.optJSONObject(NAME_KEY);
    }

    private static String getJsonMainName(JSONObject jsonNameObject){
        return jsonNameObject.optString(MAIN_NAME_KEY);
    }
    private static List<String> getJsonAlsoKnownAs(JSONObject jsonNameObject){
        JSONArray alsoKnownAs = jsonNameObject.optJSONArray(ALSO_KNOWN_AS_KEY);
        ArrayList<String> alsoKnownAsArrayList = (ArrayList<String>) getListFromJsonArray(alsoKnownAs);
        return alsoKnownAsArrayList;
    }

    private static String getPlaceOfOriginJson(JSONObject jsonObject){
        return jsonObject.optString(PLACE_OF_ORIGIN_KEY);
    }

    private static String getDescriptionJson(JSONObject jsonObject){
        return jsonObject.optString(DESCRIPTION_KEY);
    }

    private static String getImageJson(JSONObject jsonObject){
        return jsonObject.optString(IMAGE_KEY);
    }

    private static List<String> getIngredientsJson(JSONObject jsonObject){
        JSONArray ingredients = null;
        ingredients = jsonObject.optJSONArray(INGREDIENTS_KEY);
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
