package com.example.android.bakingfun.fragmentsdata;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.bakingfun.DetailedActivityView;
import com.example.android.bakingfun.R;
import com.example.android.bakingfun.Recipe;
import com.example.android.bakingfun.RecipeStepsActivity;
import com.example.android.bakingfun.RecyclerIngredientsAdapter;
import com.example.android.bakingfun.RecyclerViewSteps;
import com.example.android.bakingfun.Step;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepsFragment extends Fragment {
    List<Step> steps;
    public static int stepPosition;
    private final String RECIPIE_D = "recipie_data";
    private final String PHONE = "booleanPhone";
    private boolean isPhone;
    private Recipe recipe;
    public StepsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_steps, container, false);
        if(savedInstanceState != null){
            if(savedInstanceState.containsKey(RECIPIE_D)){
                recipe = (Recipe)savedInstanceState.getSerializable(RECIPIE_D);
                isPhone = savedInstanceState.getBoolean(PHONE);
            }
        }
        steps = recipe.getSteps();
        displaySteps(steps,view);
        return view;
    }

    private void displaySteps(final List<Step> steps, View view){
        //final List<String> stepNames = getStepNames(steps);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.steps_lv);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerViewSteps mAdapter = new RecyclerViewSteps(getContext(), steps,isPhone);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(RECIPIE_D,recipe);
        outState.putBoolean(PHONE,isPhone);
    }

    private List<String> getStepNames(List<Step> procedure){
        List<String> names = new ArrayList<>();
        for(int i=0; i<procedure.size(); i++){
            names.add(procedure.get(i).getShortDescription());
        }
        return names;
    }
    public void setRecipe(Recipe r){
        recipe = r;
    }
    public void setPhone(boolean phone){
        isPhone = phone;
    }


}

