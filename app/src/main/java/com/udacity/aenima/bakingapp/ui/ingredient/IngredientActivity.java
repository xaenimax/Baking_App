package com.udacity.aenima.bakingapp.ui.ingredient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.udacity.aenima.bakingapp.R;

import butterknife.ButterKnife;

public class IngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        ButterKnife.bind(this);
    }
}
