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


public class TutorialPageAdapter extends PagerAdapter {

    public final static int Onboarding_1 = 0;
    public final static int Onboarding_2 = 1;
    public final static int Onboarding_3 = 2;
    public final static int Onboarding_4 = 3;
    public final static int Onboarding_5 = 4;

    private Context mContext;
    private int[] mTutorialDrawableIds;
    private int[] mTutorialTextIds;
    private int[] mTutorialTitleTextIds;
    private int[] mTutorialSubtitleTextIds;
    private int mCurrentFragmentIndex;


    public TutorialPageAdapter(Context context) {
        mContext = context;
        mTutorialDrawableIds = new int[]{R.drawable.onboarding_1, R.drawable.onboarding_2, R.drawable.onboarding_3, R.drawable.onboarding_4, R.drawable.onboarding_5 };
        mTutorialTextIds = new int[]{R.string.tutorial_desc_1_explore, R.string.tutorial_desc_2_plan, R.string.tutorial_desc_3_shop, R.string.tutorial_desc_4_build, R.string.tutorial_desc_5_manage};
        mTutorialTitleTextIds = new int[]{R.string.tutorial_title_1, R.string.tutorial_title_2, R.string.tutorial_title_3, R.string.tutorial_title_4, R.string.tutorial_title_5};
        mTutorialSubtitleTextIds = new int[]{R.string.tutorial_subtitle_1, R.string.tutorial_subtitle_2, R.string.tutorial_subtitle_3, R.string.tutorial_subtitle_4, R.string.tutorial_subtitle_5};
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        final RelativeLayout itemView = (RelativeLayout) inflater.inflate(R.layout.tutorial_pager_item, container, false);
        final TextView titleTextView = (TextView) itemView.findViewById(R.id.tutorial_title);
        final TextView subtitleTextView = (TextView) itemView.findViewById(R.id.tutorial_subtitle);
        final ImageView imageView = (ImageView) itemView.findViewById(R.id.tutorial_imageView);
        final TextView textView = (TextView) itemView.findViewById(R.id.tutorial_description);
        textView.setText(mTutorialTextIds[position]);
        titleTextView.setText(mTutorialTitleTextIds[position]);
        subtitleTextView.setText(mTutorialSubtitleTextIds[position]);
        imageView.setImageResource(mTutorialDrawableIds[position]);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    @Override
    public int getCount() {
        return mTutorialDrawableIds.length;
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
