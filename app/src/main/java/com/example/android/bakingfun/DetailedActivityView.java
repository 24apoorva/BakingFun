package com.example.android.bakingfun;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.bakingfun.fragmentsdata.DetailsStepsFragment;
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

import java.util.List;

public class DetailedActivityView extends AppCompatActivity {
    public static final String DETAILED_DATA = "detailed data";
    public static final String STEP_POSITION = "position";
    private static final String TAG = "DetailedStepsActivity";
    private final String NUMBER = "position number";
    private final String TIME = "time";
    private View mContentView;
    //    @BindView(R.id.next_button)
//    ImageButton nextButton;
//    @BindView(R.id.pre_button)
//    ImageButton preButton;
    private int position;
    private Step playingStep;
    private List<Step> steps;
    private long timePosition;
    private String title;
    private ImageButton preButton;
    private ImageButton nextButton;
    private Boolean isLand;
    //Define Field for SimpleExoPlayer
    private SimpleExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        mContentView = findViewById(R.id.steps_details);
        Intent intent = getIntent();
        Recipe recipe = (Recipe) intent.getSerializableExtra(DETAILED_DATA);
        steps = recipe.getSteps();
        title = recipe.getName();
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(NUMBER)) {
                position = savedInstanceState.getInt(NUMBER);
                if (savedInstanceState.containsKey(TIME)) {
                    timePosition = savedInstanceState.getLong(TIME);
                }
            }
        } else {
            position = intent.getIntExtra(STEP_POSITION, 0);
        }
        playingStep = steps.get(position);
        if(findViewById(R.id.scrollView) == null){
            isLand = true;
            initializeLandVideo(playingStep);
        }else {
            isLand = false;
            initializeVideo(playingStep);
        }

        nextButtonClicked(position, steps);
        prevButtonClicked(position, steps);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NUMBER, position);
        outState.putLong(TIME, timePosition);
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.setPlayWhenReady(true);
        player.seekTo(timePosition);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause called");
        if (player != null) {
            timePosition = player.getCurrentPosition();
        }
        player.setPlayWhenReady(false);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();

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

    private void initializeVideo(Step step) {
        setTitle(title+" : "+step.getShortDescription());
        SimpleExoPlayerView exoPlayerView = findViewById(R.id.video_view);
        exoPlayerView.setVisibility(View.VISIBLE);
        String url = step.getVideoURL();
        if (url == null || url.isEmpty()) {
            url = step.getThumbnailURL();
            if (url == null || url.isEmpty()) {
                exoPlayerView.setVisibility(View.GONE);
            }
        }
        TextView descriptionTextView = (TextView) findViewById(R.id.description_tv);
        TextView stepNumbersTextView = (TextView) findViewById(R.id.stepnumbers_tv);
        String s = (position) + "/" + (steps.size()-1);
        if(position == 0){
            stepNumbersTextView.setVisibility(View.INVISIBLE);
        }else {
            stepNumbersTextView.setVisibility(View.VISIBLE);
            stepNumbersTextView.setText(s);
        }

        String description = step.getDescription();
        descriptionTextView.setText(description);
        //Initializing ExoPlayer
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getApplicationContext()),
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

    private void initializeLandVideo(Step step) {
        setTitle(title+" : "+step.getShortDescription());
        SimpleExoPlayerView exoPlayerView = findViewById(R.id.video_view);
        TextView descriptionTextView = (TextView) findViewById(R.id.description_tv);
        TextView stepNumbersTextView = (TextView) findViewById(R.id.stepnumbers_tv);
        exoPlayerView.setVisibility(View.VISIBLE);
        descriptionTextView.setVisibility(View.GONE);
        String url = step.getVideoURL();
        if (url == null || url.isEmpty()) {
            url = step.getThumbnailURL();
            if (url == null || url.isEmpty()) {
                exoPlayerView.setVisibility(View.GONE);
                descriptionTextView.setVisibility(View.VISIBLE);
                String description = step.getDescription();
                descriptionTextView.setText(description);
            }
        }

        String s = (position) + "/" + (steps.size()-1);
        if(position == 0){
            stepNumbersTextView.setVisibility(View.INVISIBLE);
        }else {
            stepNumbersTextView.setVisibility(View.VISIBLE);
            stepNumbersTextView.setText(s);
        }

        //Initializing ExoPlayer
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getApplicationContext()),
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

    private void nextButtonClicked(final int stepNumber, final List<Step> stepsList) {
        nextButton = (ImageButton)findViewById(R.id.next_button);
        if (stepNumber == steps.size() - 1) {
            nextButton.setVisibility(View.INVISIBLE);
        } else {
            nextButton.setVisibility(View.VISIBLE);
        }
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.release();
                position++;
                if (position + 1 == steps.size()) {
                    nextButton.setVisibility(View.INVISIBLE);
                } else {
                    preButton.setVisibility(View.VISIBLE);
                }
                playingStep = stepsList.get(position);
                if(isLand){
                    initializeLandVideo(playingStep);
                }else {
                    initializeVideo(playingStep);
                }

            }
        });
    }

    private void prevButtonClicked(final int stepNumber, final List<Step> stepsList) {
        preButton = (ImageButton)findViewById(R.id.pre_button);
        if (stepNumber == 0) {
            preButton.setVisibility(View.INVISIBLE);
        } else {
            preButton.setVisibility(View.VISIBLE);
        }
        preButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.release();
                position--;
                if (position == 0) {
                    preButton.setVisibility(View.INVISIBLE);
                } else {
                    nextButton.setVisibility(View.VISIBLE);
                }
                playingStep = stepsList.get(position);
                if(isLand){
                    initializeLandVideo(playingStep);
                }else {
                    initializeVideo(playingStep);
                }
            }
        });
    }

}