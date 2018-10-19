package com.example.android.bakingfun;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import java.util.List;

public class DetailedStepsViewActivity extends AppCompatActivity {
    private static final String TAG = "DetailedStepsViewActivity";
    public static final String DETAILED_DATA = "detailed data";
    public static final String STEP_POSITION = "position";
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private Button nextButton;
    private Button preButton;
    private int position;
    private List<Step> steps;
    //Define Field for SimpleExoPlayer
    private SimpleExoPlayer player;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_steps_view);
        mContentView = findViewById(R.id.steps_details);
        Intent intent = getIntent();
        Recipe recipe = (Recipe)intent.getSerializableExtra(DETAILED_DATA);
        position = intent.getIntExtra(STEP_POSITION,0);
        steps = recipe.getSteps();
        Step stepDetails = steps.get(position);
        initializeVideo(stepDetails);
        nextButtonClicked(position,steps);
        prevButtonClicked(position,steps);
    }
    @Override
    protected void onResume() {
        player.setPlayWhenReady(true);
        super.onResume();
    }

    @Override
    protected void onPause() {
        player.setPlayWhenReady(false);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        player.release();
        super.onDestroy();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(100);
    }


    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }


    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    private MediaSource createMediaSource(String videoUrl) {
        //Getting UserAgent
        String UserAgent = Util.getUserAgent(this, getString(R.string.app_name));

        //Creating Media Source
        MediaSource contentMediaSource = new ExtractorMediaSource(Uri.parse(videoUrl),
                new DefaultHttpDataSourceFactory(UserAgent),
                new DefaultExtractorsFactory(),
                null, null);
        //return media source
        return contentMediaSource;
    }

    public class VideoPlayerConfig {
        //Minimum Video you want to buffer while Playing
        public static final int MIN_BUFFER_DURATION = 9000;
        //Max Video you want to buffer during PlayBack
        public static final int MAX_BUFFER_DURATION = 30000;
        //Min Video you want to buffer before start Playing it
        public static final int MIN_PLAYBACK_START_BUFFER = 10000;
        //Min video You want to buffer when user resumes video
        public static final int MIN_PLAYBACK_RESUME_BUFFER = 10000;
    }
    private void initializeVideo(Step step){
        SimpleExoPlayerView exoPlayerView = findViewById(R.id.video_view);
        exoPlayerView.setVisibility(View.VISIBLE);
        String url = step.getVideoURL();
        if(url == null || url.isEmpty()){
            url = step.getThumbnailURL();
            if(url == null || url.isEmpty()){
                exoPlayerView.setVisibility(View.GONE);
            }
        }
        TextView descriptionTextView = (TextView)findViewById(R.id.description_tv);
        TextView stepNumbersTextView = (TextView) findViewById(R.id.stepnumbers_tv);
        String s = (position+1)+"/"+steps.size();
        stepNumbersTextView.setText(s);
        String description = step.getDescription();
        descriptionTextView.setText(description);
            //Biding xml view to exoPlayerView object

            //Creating Load Control
            LoadControl loadControl = new DefaultLoadControl(
                    new DefaultAllocator(true, 16)
                    ,VideoPlayerConfig.MIN_BUFFER_DURATION,
                    VideoPlayerConfig.MAX_BUFFER_DURATION,
                    VideoPlayerConfig.MIN_PLAYBACK_START_BUFFER,
                    VideoPlayerConfig.MIN_PLAYBACK_RESUME_BUFFER);

            //Initializing ExoPlayer
            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getApplicationContext()),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());
            //binding exoPlayerView to SimpleExoPlayer
            exoPlayerView.setPlayer(player);
            //preparing player with media Source
            player.prepare(createMediaSource(url));
            player.setPlayWhenReady(true);
            //Uncomment following line remove above line if you want to play Ads between Video
            //player.prepare(createMediaSourceWithAds(url,exoPlayerView));
            //adding Listener to SimpleExoPlayer
            player.addListener(new Player.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest) {
                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                }

                @Override
                public void onLoadingChanged(boolean isLoading) {
                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    switch (playbackState) {
                        case Player.STATE_BUFFERING:
                            //Player is in state State buffering show some loading progress
                            //showProgress();
                            break;
                        case Player.STATE_READY:
                            //Player is ready to Play. Remove loading progress
                            // hideProgress();
                            break;
                    }
                }

                @Override
                public void onRepeatModeChanged(int repeatMode) {
                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {
                }

                @Override
                public void onPositionDiscontinuity() {
                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                }
            });

        }

        private void nextButtonClicked(final int stepNumber, final List<Step> stepsList){
        nextButton = (Button)findViewById(R.id.next_button);
        if(stepNumber == steps.size()-1 ){
            nextButton.setVisibility(View.INVISIBLE);
        }else {
            nextButton.setVisibility(View.VISIBLE);
        }
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.release();
                position++;
                if(position+1 == steps.size()){
                    nextButton.setVisibility(View.INVISIBLE);
                }else {
                    preButton.setVisibility(View.VISIBLE);
                }
                initializeVideo(stepsList.get(position));
            }
        });
        }

    private void prevButtonClicked(final int stepNumber, final List<Step> stepsList){
        preButton = (Button)findViewById(R.id.pre_button);
        if(stepNumber == 0 ){
            preButton.setVisibility(View.INVISIBLE);
        }else {
            preButton.setVisibility(View.VISIBLE);
        }
        preButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.release();
                position--;
                if(position == 0 ){
                    preButton.setVisibility(View.INVISIBLE);
                }else {
                    nextButton.setVisibility(View.VISIBLE);
                }
                initializeVideo(stepsList.get(position));
            }
        });
    }
    }

