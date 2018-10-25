package com.example.android.bakingfun;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RviewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private Recipe[] recipeNames;
    private Context context;
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
        rviewHolder.name.setOnClickListener(new View.OnClickListener() {
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

    public class RviewHolder extends RecyclerView.ViewHolder{
        TextView name;
        public RviewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.recipe_name);


        }
    }

}
