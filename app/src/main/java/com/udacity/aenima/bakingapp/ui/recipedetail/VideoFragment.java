package com.udacity.aenima.bakingapp.ui.recipedetail;


import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udacity.aenima.bakingapp.R;
import com.udacity.aenima.bakingapp.data.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends Fragment {
    private static final String CURRENT_STEP_ARG = "current_step_arg";
    private static final String STEP_LIST_ARG = "step_list_arg";
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    @BindView(R.id.media_player_ep)
    public PlayerView mPlayerView;
    private SimpleExoPlayer mSimpleExoPlayer;

    private List<Step> mStepList;
    private int currentIndex = 0;

    public VideoFragment() {
        // Required empty public constructor
    }

    public void setStepList(List<Step> stepList, int selectedIndex) {
        mStepList = stepList;
        if(selectedIndex < mStepList.size())
            currentIndex = selectedIndex;
        playVideo();
    }

    private void playVideo() {
        if(mSimpleExoPlayer == null){
            initializeExpoPlayer();
        }

        if(mStepList != null) {
            String uri = mStepList.get(currentIndex).videoUrl;

            Uri mediaUri = Uri.parse(uri);
            String userAgent = Util.getUserAgent(getActivity(), "BakingAppVideo");
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getActivity(), userAgent);
            MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(mediaUri);

            mSimpleExoPlayer.prepare(mediaSource);
            // Bind the player to the view.
            mPlayerView.setPlayer(mSimpleExoPlayer);

            mSimpleExoPlayer.setPlayWhenReady(true);
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment VideoFragment.
     */
    public static VideoFragment newInstance(List<Step> stepList, int selectedIndex) {
        VideoFragment fragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CURRENT_STEP_ARG, selectedIndex);
        bundle.putParcelableArrayList(STEP_LIST_ARG, new ArrayList<Parcelable>(stepList));

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            currentIndex = bundle.getInt(CURRENT_STEP_ARG, 0);
            mStepList = bundle.getParcelableArrayList(STEP_LIST_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        ButterKnife.bind(this, view);

        playVideo();

        return view;
    }

    private void initializeExpoPlayer() {
        if (mSimpleExoPlayer == null) {
            // 1. Create a default TrackSelector
            //Handler mainHandler = new Handler();
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);
            //LoadControl loadControl = new DefaultLoadControl();

            // 2. Create the player
            mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

        }

    }

    @Override
    public void onDestroy() {
        releasePlayer();
        super.onDestroy();
    }

    private void releasePlayer() {
        if(mSimpleExoPlayer != null) {
            mSimpleExoPlayer.stop();
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
        }
    }

}
