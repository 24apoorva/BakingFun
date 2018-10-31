package com.example.android.bakingfun;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bakingfun.fragmentsdata.DetailsStepsFragment;
import com.example.android.bakingfun.fragmentsdata.StepsFragment;

import java.util.List;

public class RecyclerViewSteps extends RecyclerView.Adapter<RecyclerViewSteps.StepsViewHolder>{

    private Context context;
    private List<Step> step;
    private boolean phone;
    private int row_index = -1;

    public RecyclerViewSteps(Context context, List<Step> step, boolean phone){
        this.context = context;
        this.step = step;
        this.phone = phone;
    }
    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_view,viewGroup,false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder stepsViewHolder, final int i) {
        final String stepDes = step.get(i).getShortDescription();
        stepsViewHolder.stepsText.setText(stepDes);
        stepsViewHolder.stepsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = i;
                notifyDataSetChanged();
                if(!phone){
                    DetailsStepsFragment detailsStepsFragment = new DetailsStepsFragment();
                    detailsStepsFragment.setPosition(i);
                    detailsStepsFragment.setStep(step.get(i));
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.right_frame, detailsStepsFragment).commit();
                }else{
                    Intent detailedViewIntent = new Intent(context, DetailedActivityView.class);
                    detailedViewIntent.putExtra(DetailedActivityView.DETAILED_DATA,RecipeStepsActivity.recipe);
                    detailedViewIntent.putExtra(DetailedActivityView.STEP_POSITION,i);
                    context.startActivity(detailedViewIntent);
                }

            }
        });

        if(row_index == i){
            stepsViewHolder.stepsText.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        }else {
            stepsViewHolder.stepsText.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }


    @Override
    public int getItemCount() {
        return step.size();
    }

    class StepsViewHolder extends RecyclerView.ViewHolder{

        TextView stepsText;
        StepsViewHolder(@NonNull View itemView) {
            super(itemView);
            stepsText = itemView.findViewById(R.id.list_textView);

        }
    }
}
