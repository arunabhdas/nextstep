package com.arunabhdas.mobile.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arunabhdas.mobile.broccoli.R;


/**
 * OnboardingPageAdapter
 */
public class OnboardingPageAdapter extends PagerAdapter {

    public final static int Onboarding_Roaming = 0;
    public final static int Onboarding_Usage = 1;
    public final static int Onboarding_DataPackage = 2;

    private Context mContext;
    private int[] mOnboardingDrawableIds;
    private int[] mOnboardingTextIds;
    private int mCurrentFragmentIndex;

    public OnboardingPageAdapter(Context context) {
        mContext = context;
        mOnboardingDrawableIds = new int[]{R.drawable.accessnow_400x400, R.drawable.accessnow_discover, R.drawable.accessnow_rate};
        mOnboardingTextIds = new int[]{R.string.tutorial_desc_1_explore, R.string.tutorial_desc_2_plan, R.string.tutorial_desc_3_shop};
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        final RelativeLayout itemView = (RelativeLayout) inflater.inflate(R.layout.onboarding_pager_item, container, false);
        final ImageView imageView = (ImageView) itemView.findViewById(R.id.onboarding_imageView);
        final TextView textView = (TextView) itemView.findViewById(R.id.onboarding_description);
        textView.setText(mOnboardingTextIds[position]);
        imageView.setImageResource(mOnboardingDrawableIds[position]);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    @Override
    public int getCount() {
        return mOnboardingDrawableIds.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setCurrentFragmentIndex(int index) {
        mCurrentFragmentIndex = index;
    }

    public int getCurrentFragmentIndex() {
        return mCurrentFragmentIndex;
    }

    public int getPreviousItemIndex() {
        return Math.max(mCurrentFragmentIndex - 1, 0);
    }
}
