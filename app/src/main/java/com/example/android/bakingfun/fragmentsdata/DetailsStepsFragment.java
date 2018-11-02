package com.example.android.bakingfun.fragmentsdata;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingfun.R;
import com.example.android.bakingfun.Step;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsStepsFragment extends Fragment {
    private static final String TAG = "DetailedStepsActivity";
    private final String NUMBER = "position number";
    private final String TIME = "time";
    private final String STEP = "steps";
    private int position;
    private Step playingStep;
    private long timePosition;
    private int listIndex;
    private Step stepData;


    //Define Field for SimpleExoPlayer
    private SimpleExoPlayer player;

    public DetailsStepsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_details_steps, container, false);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(NUMBER)) {
                position = savedInstanceState.getInt(NUMBER);
                playingStep = (Step) savedInstanceState.getSerializable(STEP);
                if (savedInstanceState.containsKey(TIME)) {
                    timePosition = savedInstanceState.getLong(TIME);
                }
            }
        } else {
            position =listIndex;
            playingStep = stepData;
        }
            initializeVideo(playingStep,view);

        return view;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NUMBER, position);
        outState.putSerializable(STEP,playingStep);
        outState.putLong(TIME, timePosition);
    }

    @Override
    public void onResume() {
        super.onResume();
        player.setPlayWhenReady(true);
        player.seekTo(timePosition);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause called");
        if (player != null) {
            timePosition = player.getCurrentPosition();
            player.setPlayWhenReady(false);
        }


    }

    @Override
    public void onStop() {
        super.onStop();
        if(player != null) {
            timePosition = player.getCurrentPosition();
            releasePlayer();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();

    }

    private MediaSource createMediaSource(String videoUrl) {
        //Getting UserAgent
        String UserAgent = Util.getUserAgent(getContext(), getString(R.string.app_name));

        //Creating Media Source
        //return media source
        return new ExtractorMediaSource(Uri.parse(videoUrl),
                new DefaultHttpDataSourceFactory(UserAgent),
                new DefaultExtractorsFactory(),
                null, null);
    }

    private void initializeVideo(Step step, View view) {
        SimpleExoPlayerView exoPlayerView = view.findViewById(R.id.video_view);
        exoPlayerView.setVisibility(View.VISIBLE);
        String url = step.getVideoURL();
        if (url == null || url.isEmpty()) {
            url = step.getThumbnailURL();
            if (url == null || url.isEmpty()) {
                exoPlayerView.setVisibility(View.GONE);
            }
        }
        TextView descriptionTextView = view.findViewById(R.id.description_tv);

        String description = step.getDescription();
        descriptionTextView.setText(description);
        //Initializing ExoPlayer
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(),
                new DefaultLoadControl());
        //binding exoPlayerView to SimpleExoPlayer
        exoPlayerView.setPlayer(player);
        player.setPlayWhenReady(true);
        //preparing player with media Source
        player.prepare(createMediaSource(url));
        //Uncomment following line remove above line if you want to play Ads between Video
        //player.prepare(createMediaSourceWithAds(url,exoPlayerView));
        //adding Listener to SimpleExoPlayer
        if (timePosition == C.TIME_UNSET) {
            player.seekTo(timePosition);
        }

    }


    public void setPosition(int index){
        listIndex = index;
    }

    public void setStep(Step step){
        stepData = step;
    }
    /**
     * Method to release exoPlayer
     */
    private void releasePlayer(){
        if(player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

}
