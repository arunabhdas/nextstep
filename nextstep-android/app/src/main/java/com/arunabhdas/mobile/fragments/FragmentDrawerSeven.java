package com.arunabhdas.mobile.fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.arunabhdas.mobile.broccoli.MainActivity;
import com.arunabhdas.mobile.broccoli.R;
import com.arunabhdas.mobile.utils.Constants;
import com.arunabhdas.mobile.views.HorizontalProgressBarView;

import java.util.List;


public class FragmentDrawerSeven extends Fragment {
    private static final String TAG = "FragmentDrawerSeven";
    private HorizontalProgressBarView vRemainingBar;
    private TextView vTopPanelTitle;
    private int mProgressStartColor;
    private int mProgressEndColor;
    private Button mButtonPlanEnable;
    private Button mManageEsim;
    private Button mDownloadEsimProfile;
    private Context mContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = this.getContext();
        View view = View.inflate(getContext(), R.layout.fragment_drawer_seven, null);
        vRemainingBar = (HorizontalProgressBarView) view.findViewById(R.id.package_remaining_bar_chart);
        vTopPanelTitle = (TextView) view.findViewById(R.id.top_panel_title);
        mManageEsim = (Button) view.findViewById(R.id.manage_esim);
        mDownloadEsimProfile = (Button) view.findViewById(R.id.download_esim_profile);
        mButtonPlanEnable = (Button) view.findViewById(R.id.button_plan_enable);

        mButtonPlanEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainActivity.class);
                sendExplicitBroadcast(mContext, intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupRemainingBar();


    }

    protected void setupRemainingBar() {
        if (vRemainingBar != null) {
            vRemainingBar.setGradientStartColor(getResources().getColor(R.color.mifi_bar_start_color));
            vRemainingBar.setGradientEndColor(getResources().getColor(R.color.mifi_bar_end_color));
            float timeUnusedFraction = 0.5f;
            vRemainingBar.setAnimationEndFraction(timeUnusedFraction);
            vRemainingBar.startAnimation();
        }
    }

    // NetworkIndicator.OnMoveListener callbacks

    private void sendExplicitBroadcast(Context context, Intent intent) {
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> matches= pm.queryBroadcastReceivers(new Intent(Constants.MOBILE_DATA_ENABLE_INTENT_ACTION), 0);

        Log.d(TAG, "[VSimMobileDataBroadcastReceiver] Finding Match ");
        if(matches != null)
        {
            Log.d(TAG, "[VSimMobileDataBroadcastReceiver] Finding items " + matches.size());

        }

        for (ResolveInfo resolveInfo : matches) {
            Intent explicit=new Intent(intent);

            Log.d(TAG, "[VSimMobileDataBroadcastReceiver] Found match Match " + resolveInfo.activityInfo.applicationInfo.packageName);

            Log.d(TAG, "[VSimMobileDataBroadcastReceiver] Found match Match " + resolveInfo.activityInfo.name);


            ComponentName cn=
                    new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName,
                            resolveInfo.activityInfo.name);

            explicit.setComponent(cn);
            context.getApplicationContext().sendBroadcast(explicit);
        }
    }


}
