package com.example.android.bakingfun;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.bakingfun.fragmentsdata.FragmentSelectionAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RecipeStepsActivity extends AppCompatActivity {
    private static final String TAG = "RecipeStepsActivity";
    public static final String STEPS_DATA = "steps details";
    public static Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        recipe = (Recipe) intent.getSerializableExtra(STEPS_DATA);
        setTitle(recipe.getName());
        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = findViewById(R.id.viewpager);
        // Create an adapter that knows which fragment should be shown on each page
        FragmentSelectionAdapter adapter = new FragmentSelectionAdapter(getSupportFragmentManager(), this);

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
