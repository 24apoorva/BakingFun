package com.example.android.bakingfun;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RviewHolder>{

    private final Recipe[] recipeNames;
    private final Context context;
    public RecyclerViewAdapter(Context context,Recipe[] recipeNames){
        this.context = context;
        this.recipeNames = recipeNames;
    }

    @NonNull
    @Override
    public RviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerviewl_list_card,viewGroup,false);
        return new RviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RviewHolder rviewHolder, int i) {
        final Recipe recipe = recipeNames[i];
        rviewHolder.name.setText(recipe.getName());
        String srv = context.getResources().getString(R.string.servings_tv) + recipe.getServings();
        rviewHolder.servings.setText(srv);
        String ing = context.getResources().getString(R.string.ingredients) + ": "+recipe.getIngredients().size();
        rviewHolder.ingSize.setText(ing);
        rviewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecipeStepsActivity.class);
                intent.putExtra(RecipeStepsActivity.STEPS_DATA,recipe);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeNames.length;
    }

    class RviewHolder extends RecyclerView.ViewHolder{
        final TextView name;
        final TextView servings;
        final TextView ingSize;
        final CardView card;
        RviewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.recipe_name);
            servings = itemView.findViewById(R.id.servings);
            ingSize = itemView.findViewById(R.id.ing_no);
            card = itemView.findViewById(R.id.main_cd);
        }
    }

}
