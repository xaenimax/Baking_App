package com.udacity.aenima.bakingapp.ui.recipedetail;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
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
    private static final String ARG_CURRENT_STEP = "current_step_arg";
    private static final String ARG_STEP_LIST = "step_list_arg";
    private static final String ARG_CURRENT_VIDEO_POSITION = "arg_current_video_position" ;

    @BindView(R.id.media_player_ep)
    public PlayerView mPlayerView;
    @BindView(R.id.next_btn)
    Button nextButton;
    @BindView(R.id.previous_btn)
    Button prevButton;
    @BindView(R.id.step_description_tv)
    TextView stepDescription;
    @BindView(R.id.step_short_description_tv)
    TextView stepShortDescription;


    long currentPosition = C.TIME_UNSET;
    private SimpleExoPlayer mSimpleExoPlayer;

    private List<Step> mStepList;
    private int currentIndex = 0;
    private boolean wasPlayingVideo;

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
       initializeExpoPlayer();
       if(mStepList != null) {
           String uri = mStepList.get(currentIndex).videoUrl;

           Uri mediaUri = Uri.parse(uri);
           String userAgent = Util.getUserAgent(getActivity(), "BakingAppVideo");
           DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getActivity(), userAgent);
           MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(mediaUri);

           mSimpleExoPlayer.prepare(mediaSource);
           // Bind the player to the view.
           mPlayerView.setPlayer(mSimpleExoPlayer);
           mSimpleExoPlayer.seekTo(currentPosition);

           mSimpleExoPlayer.setPlayWhenReady(true);

           stepShortDescription.setText(mStepList.get(currentIndex).shortDescription);
           stepDescription.setText(mStepList.get(currentIndex).description);
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
        bundle.putInt(ARG_CURRENT_STEP, selectedIndex);
        bundle.putParcelableArrayList(ARG_STEP_LIST, new ArrayList<Parcelable>(stepList));

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey(ARG_CURRENT_STEP) && bundle.containsKey(ARG_STEP_LIST)) {
            currentIndex = bundle.getInt(ARG_CURRENT_STEP, 0);
            mStepList = bundle.getParcelableArrayList(ARG_STEP_LIST);
        }
        if(savedInstanceState != null && savedInstanceState.containsKey(ARG_CURRENT_VIDEO_POSITION)) {
            currentPosition =  savedInstanceState.getLong(ARG_CURRENT_VIDEO_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        ButterKnife.bind(this, view);

        playVideo();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextSelected();
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPreviousSelected();
            }
        });


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
    public void onStop() {
        wasPlayingVideo = mSimpleExoPlayer.getPlayWhenReady();
        currentPosition = mSimpleExoPlayer.getCurrentPosition();
        mSimpleExoPlayer.setPlayWhenReady(false);
        super.onStop();
    }

    @Override
    public void onStart() {
        if (mSimpleExoPlayer == null) {
            initializeExpoPlayer();
        }
        mSimpleExoPlayer.seekTo(currentPosition);
        mSimpleExoPlayer.setPlayWhenReady(wasPlayingVideo);
        super.onStart();
    }

    @Override
    public void onDestroy() {
        releasePlayer();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong(ARG_CURRENT_VIDEO_POSITION, mSimpleExoPlayer.getCurrentPosition());
        outState.putInt(ARG_CURRENT_STEP, currentIndex);
        super.onSaveInstanceState(outState);
    }

    private void releasePlayer() {
        if(mSimpleExoPlayer != null) {
            mSimpleExoPlayer.stop();
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
        }
    }

    private void onPreviousSelected(){
        if(currentIndex > 0){
            mSimpleExoPlayer.stop();
            currentIndex --;
            playVideo();
        }else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), R.string.no_more_backwards_videos, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void onNextSelected(){
        if(currentIndex < mStepList.size() -1){
            mSimpleExoPlayer.stop();
            currentIndex ++;
            playVideo();
        }else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), R.string.no_more_forwards_videos, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
