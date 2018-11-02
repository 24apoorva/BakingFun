package com.example.android.bakingfun.fragmentsdata;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingfun.Ingredient;
import com.example.android.bakingfun.R;
import com.example.android.bakingfun.Recipe;
import com.example.android.bakingfun.RecyclerIngredientsAdapter;

import java.util.List;

public class IngredientsFragment extends Fragment {
    private boolean isPhone;
    private Recipe mRecipe;
    private final String ING_LIST = "ing list";
    private final String PHONE_VALUE = "isphone";

    public IngredientsFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingrediants, container, false);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(ING_LIST)) {
                mRecipe = (Recipe) savedInstanceState.getSerializable(ING_LIST);
                isPhone = savedInstanceState.getBoolean(PHONE_VALUE);
            }
        }
        List<Ingredient> ingredients = mRecipe.getIngredients();
            displayIngredients(ingredients, view);
            return view;
        }


        private void displayIngredients (List < Ingredient > list, View view){
            RecyclerView mRecyclerView = view.findViewById(R.id.ingredients);
            RecyclerView.LayoutManager mLayoutManager;
            if (!isPhone) {
                mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            } else {
                mLayoutManager = new LinearLayoutManager(getContext());
            }

            mRecyclerView.setLayoutManager(mLayoutManager);
            RecyclerIngredientsAdapter mAdapter = new RecyclerIngredientsAdapter(getContext(), list, isPhone);
            mRecyclerView.setAdapter(mAdapter);
        }

        @Override
        public void onSaveInstanceState (@NonNull Bundle outState){
            super.onSaveInstanceState(outState);
            outState.putSerializable(ING_LIST, mRecipe);
            outState.putBoolean(PHONE_VALUE,isPhone);
        }

        public void setType ( boolean phone){
            isPhone = phone;
        }
//   public void setIng(List<Ingredient> ing){
//       List<String> singleLine = new ArrayList<>();
//           for(int i=0; i<ing.size(); i++){
//               String name = ing.get(i).getIngredient();
//               String measure = ing.get(i).getMeasure();
//               double quantity = ing.get(i).getQuantity();
//               String fullData = quantity+" "+measure+" "+name;
//               singleLine.add(fullData);
//           }
//       ingredients = singleLine;
//       }

        public void setmRecipe (Recipe recipe){
            mRecipe = recipe;
        }

    }



