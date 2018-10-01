package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView alsoKnownAsTextView;
    private TextView ingredientsTextView;
    private TextView placeOfOriginTextView;
    private TextView descriptionTextView;
    private ImageView ingredientsIv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage()).error(R.mipmap.ic_launcher)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void init(){
        ingredientsIv = findViewById(R.id.image_iv);
        alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);
        placeOfOriginTextView = findViewById(R.id.origin_tv);
        descriptionTextView = findViewById(R.id.description_tv);
    }

    private void populateUI(Sandwich sandwich) {
        if (sandwich.getAlsoKnownAs().isEmpty()){
            alsoKnownAsTextView.setText("No cool nicknames");
        }else{
            for(String nicknames: sandwich.getAlsoKnownAs()){
                if(getLastElementOnList(sandwich.getAlsoKnownAs()).equals(nicknames)){
                    alsoKnownAsTextView.append(nicknames);
                }else{
                    alsoKnownAsTextView.append(nicknames+", ");
                }
            }
        }
        for(String ingredient: sandwich.getIngredients()){
            if(getLastElementOnList(sandwich.getIngredients()).equals(ingredient)){
                ingredientsTextView.append(ingredient);
            }else{
                ingredientsTextView.append(ingredient+", ");
            }
        }
        if(sandwich.getPlaceOfOrigin().isEmpty()){
            placeOfOriginTextView.setText("Place of origin is unknown");
        }else{
            placeOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        }
        descriptionTextView.setText(sandwich.getDescription());

    }

    private String getLastElementOnList(List<String> list){
        return  list.get(list.size() -1);
    }
}
