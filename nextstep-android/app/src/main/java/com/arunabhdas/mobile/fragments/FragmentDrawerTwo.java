package com.arunabhdas.mobile.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.arunabhdas.mobile.broccoli.MainActivity;
import com.arunabhdas.mobile.broccoli.R;
import com.arunabhdas.mobile.views.HorizontalProgressBarView;

public class FragmentDrawerTwo extends Fragment{

    private static final String TAG = "FragmentDrawerTwo";
    private HorizontalProgressBarView vRemainingBar;
    private TextView vTopPanelTitle;
    private int mProgressStartColor;
    private int mProgressEndColor;
    private Button mButtonPlanEnable;
    private Button mManageEsim;
    private Button mDownloadEsimProfile;
    private Context mContext;
    private GoogleMap vMapView;
    private SupportMapFragment mapFragment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = this.getContext();
        View view = View.inflate(getContext(), R.layout.fragment_drawer_two, null);
        vRemainingBar = (HorizontalProgressBarView) view.findViewById(R.id.package_remaining_bar_chart);
        vTopPanelTitle = (TextView) view.findViewById(R.id.top_panel_title);
        mManageEsim = (Button) view.findViewById(R.id.manage_esim);
        mDownloadEsimProfile = (Button) view.findViewById(R.id.download_esim_profile);
        mButtonPlanEnable = (Button) view.findViewById(R.id.button_plan_enable);


        return view;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }
}
