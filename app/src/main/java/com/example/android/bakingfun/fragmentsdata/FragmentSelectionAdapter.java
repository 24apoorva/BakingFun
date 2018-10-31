package com.example.android.bakingfun.fragmentsdata;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.bakingfun.R;
import com.example.android.bakingfun.Recipe;

public class FragmentSelectionAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private Recipe mrecipe;
    IngredientsFragment mingredientsFragment;
    StepsFragment mstepsFragment;
    public FragmentSelectionAdapter(FragmentManager fm,Context context, IngredientsFragment ingredientsFragment,StepsFragment stepsFragment) {
        super(fm);
        mContext = context;
        mingredientsFragment = ingredientsFragment;
        mstepsFragment = stepsFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
//                IngredientsFragment ingredientsFragment = new IngredientsFragment();
//                ingredientsFragment.setType(true);
//                ingredientsFragment.setIng(mrecipe.getIngredients());
                return mingredientsFragment ;
            default:
//                StepsFragment stepsFragment = new StepsFragment();
//                stepsFragment.setSteps(mrecipe.getSteps());
                return mstepsFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return (mContext.getString(R.string.ingredients));
            default:
                return (mContext.getString(R.string.steps));
        }
    }

}
