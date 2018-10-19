package com.example.android.bakingfun.fragmentsdata;

import android.content.Context;
import android.icu.text.UnicodeSetSpanner;
import android.icu.util.ValueIterator;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingfun.Ingredient;
import com.example.android.bakingfun.R;
import com.example.android.bakingfun.RecipeStepsActivity;
import com.example.android.bakingfun.Step;

import java.util.ArrayList;
import java.util.List;

public class IngredientsFragment extends Fragment {
    public IngredientsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingrediants, container, false);
        ListView lv = view.findViewById(R.id.ingredients);
        List<Ingredient> ingredients = RecipeStepsActivity.recipe.getIngredients();
        displayIngredients(ingredients,lv);
        return view;
    }

    private void displayIngredients(List<Ingredient> list, ListView view){
        List<String> fullData = getIngredientsFull(list);
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, fullData);
        view.setAdapter(mArrayAdapter);
    }
    private List<String> getIngredientsFull(List<Ingredient> ingredients){
        List<String> singleLine = new ArrayList<>();
        for(int i=0; i<ingredients.size(); i++){
            String name = ingredients.get(i).getIngredient();
            String measure = ingredients.get(i).getMeasure();
            double quantity = ingredients.get(i).getQuantity();
            String fullData = quantity+" "+measure+" "+name;
            singleLine.add(fullData);
        }
        return singleLine;
    }
}

