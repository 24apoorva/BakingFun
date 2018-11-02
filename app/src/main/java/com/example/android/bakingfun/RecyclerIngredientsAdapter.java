package com.example.android.bakingfun;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class RecyclerIngredientsAdapter extends RecyclerView.Adapter<RecyclerIngredientsAdapter.IngredientsViewHolder> {

    private final List<Ingredient> ingredient;
    private final boolean isPhone;

    public RecyclerIngredientsAdapter(Context context, List<Ingredient> ingredient, boolean isPhone){
        this.ingredient = ingredient;
        this.isPhone = isPhone;
    }
    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view,viewGroup,false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder ingredientsViewHolder, int i) {
        final String ing = ingredient.get(i).getIngredient();
        ingredientsViewHolder.ing.setText(ing);
    }

    @Override
    public int getItemCount() {
        return ingredient.size();
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder{

        final TextView ing;
        IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            ing = itemView.findViewById(R.id.card_textview);
            if(isPhone){
                LinearLayout linearLayout = itemView.findViewById(R.id.l);
                LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                   linearLayout.setLayoutParams(l);
            }
        }
    }
}
