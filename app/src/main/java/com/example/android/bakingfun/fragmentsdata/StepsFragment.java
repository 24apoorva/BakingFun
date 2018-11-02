package com.example.android.bakingfun.fragmentsdata;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingfun.R;
import com.example.android.bakingfun.Recipe;
import com.example.android.bakingfun.RecyclerViewSteps;
import com.example.android.bakingfun.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepsFragment extends Fragment {
    private final String RECIPIE_D = "recipie_data";
    private final String PHONE = "booleanPhone";
    private boolean isPhone;
    private Recipe recipe;
    public StepsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_steps, container, false);
        if(savedInstanceState != null){
            if(savedInstanceState.containsKey(RECIPIE_D)){
                recipe = (Recipe)savedInstanceState.getSerializable(RECIPIE_D);
                isPhone = savedInstanceState.getBoolean(PHONE);
            }
        }
        List<Step> steps = recipe.getSteps();
        displaySteps(steps,view);
        return view;
    }

    private void displaySteps(final List<Step> steps, View view){
        //final List<String> stepNames = getStepNames(steps);

        RecyclerView mRecyclerView = view.findViewById(R.id.steps_lv);
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
    public void setRecipe(Recipe r){
        recipe = r;
    }
    public void setPhone(boolean phone){
        isPhone = phone;
    }


}