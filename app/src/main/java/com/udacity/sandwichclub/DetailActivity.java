package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.udacity.sandwichclub.utils.StringUtils.join;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private final Map<TextView, TextView> labels = new HashMap<>();

    private ActivityDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

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

        registerLabels();

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mBinding.imageIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        setContent(mBinding.alsoKnownTv, sandwich.getAlsoKnownAs());
        setContent(mBinding.descriptionTv, sandwich.getDescription());
        setContent(mBinding.ingredientsTv, sandwich.getIngredients());
        setContent(mBinding.originTv, sandwich.getPlaceOfOrigin());
    }

    private void registerLabels() {
        labels.put(mBinding.alsoKnownTv, mBinding.alsoKnownTvLabel);
        labels.put(mBinding.descriptionTv, mBinding.descriptionTvLabel);
        labels.put(mBinding.ingredientsTv, mBinding.ingredientsTvLabel);
        labels.put(mBinding.originTv, mBinding.originTvLabel);
    }

    private void setContent(TextView textView, String text) {
        textView.setText(text);
        adjustVisibility(textView);
    }

    private void setContent(TextView textView, List<String> elements) {
        setContent(textView, join(elements));
    }

    private void adjustVisibility(TextView textView) {
        TextView label = labels.get(textView);

        int visibility = textView.getText().length() == 0 ? GONE : VISIBLE;
        textView.setVisibility(visibility);
        label.setVisibility(visibility);
    }
}