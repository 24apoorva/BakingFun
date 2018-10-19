package com.example.android.bakingfun.fragmentsdata;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.bakingfun.DetailedStepsViewActivity;
import com.example.android.bakingfun.R;
import com.example.android.bakingfun.RecipeStepsActivity;
import com.example.android.bakingfun.Step;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepsFragment extends Fragment {
    List<Step> steps;

    public StepsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_steps, container, false);

        ListView listView = view.findViewById(R.id.steps_lv);
        steps = RecipeStepsActivity.recipe.getSteps();
        displaySteps(steps,listView);
        return view;
    }

    private void displaySteps(final List<Step> steps, ListView view){
        List<String> stepNames = getStepNames(steps);
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(getContext(),R.layout.list_view,R.id.list_textView, stepNames);
        view.setAdapter(mArrayAdapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailedViewIntent = new Intent(getContext(), DetailedStepsViewActivity.class);
                detailedViewIntent.putExtra(DetailedStepsViewActivity.DETAILED_DATA,RecipeStepsActivity.recipe);
                detailedViewIntent.putExtra(DetailedStepsViewActivity.STEP_POSITION,position);
                getContext().startActivity(detailedViewIntent);
            }
        });
    }
    private List<String> getStepNames(List<Step> procedure){
        List<String> names = new ArrayList<>();
        for(int i=0; i<procedure.size(); i++){
            names.add(procedure.get(i).getShortDescription());
        }
        return names;
    }
}

