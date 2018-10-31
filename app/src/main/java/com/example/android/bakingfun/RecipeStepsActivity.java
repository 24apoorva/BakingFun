package com.example.android.bakingfun;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.android.bakingfun.fragmentsdata.FragmentSelectionAdapter;
import com.example.android.bakingfun.fragmentsdata.IngredientsFragment;
import com.example.android.bakingfun.fragmentsdata.StepsFragment;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepsActivity extends AppCompatActivity {
    private static final String TAG = "RecipeStepsActivity";
    public static final String STEPS_DATA = "steps details";
    public static Recipe recipe;
    private final String RECIPE_DATA = "recipie data";
    public static boolean phone;
    public static FragmentTransaction fT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if(savedInstanceState != null){
            if(savedInstanceState.containsKey(RECIPE_DATA)){
                recipe = (Recipe)savedInstanceState.getSerializable(RECIPE_DATA);
            }
        }else{
            Intent intent = getIntent();
            recipe = (Recipe) intent.getSerializableExtra(STEPS_DATA);
        }

        setTitle(recipe.getName());
        if( findViewById(R.id.viewpager) != null){
            phone = true;

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = findViewById(R.id.viewpager);
            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setType(phone);
            ingredientsFragment.setmRecipe(recipe);
            StepsFragment stepsFragment = new StepsFragment();
            stepsFragment.setRecipe(recipe);
            stepsFragment.setPhone(phone);
        // Create an adapter that knows which fragment should be shown on each page
        FragmentSelectionAdapter adapter = new FragmentSelectionAdapter(getSupportFragmentManager(), this,ingredientsFragment,stepsFragment);

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        }else{
               tabletLayout();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(RECIPE_DATA,recipe);
    }

    private void tabletLayout(){
        phone = false;
        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        ingredientsFragment.setType(phone);
        ingredientsFragment.setmRecipe(recipe);
        StepsFragment stepsFragment = new StepsFragment();
        stepsFragment.setRecipe(recipe);
        stepsFragment.setPhone(phone);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.ing_right_frame,ingredientsFragment)
                .add(R.id.left_frame,stepsFragment).commit();

    }
}