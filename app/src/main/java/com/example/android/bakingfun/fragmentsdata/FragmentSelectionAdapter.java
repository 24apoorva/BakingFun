package com.example.android.bakingfun.fragmentsdata;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.android.bakingfun.R;

public class FragmentSelectionAdapter extends FragmentPagerAdapter {
    private final Context mContext;
    private final IngredientsFragment mingredientsFragment;
    private final StepsFragment mstepsFragment;
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
                return mingredientsFragment ;
            default:
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
