package com.elmahalwy.bakingapp.Activties;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.elmahalwy.bakingapp.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsActivity extends AppCompatActivity {
    @BindView(R.id.tv_no_video)
    TextView tv_no_video;
    @BindView(R.id.tv_step_description)
    TextView tv_step_description;
    @BindView(R.id.video_steps)
    SimpleExoPlayerView video_steps;
    SimpleExoPlayer Player;
    @BindView(R.id.tv_toolbar_add_widget)
    TextView tv_toolbar_add_widget;
    static String descrption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        ButterKnife.bind(this);
        InitEventDriven();
        descrption=getIntent().getStringExtra("description");
    }

    void InitEventDriven() {
        tv_step_description.setText(getIntent().getStringExtra("description"));
        if (getIntent().getStringExtra("video_url").isEmpty()) {
            tv_no_video.setVisibility(View.VISIBLE);
            video_steps.setVisibility(View.INVISIBLE);
            tv_no_video.setText("No videos");

        } else {
            tv_no_video.setVisibility(View.GONE);
            video_steps.setVisibility(View.VISIBLE);
            PlayVideo(Uri.parse(getIntent().getStringExtra("video_url")));
        }
        tv_toolbar_add_widget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleWidgetProvider simpleWidgetProvider=new SimpleWidgetProvider();
                Toast.makeText(StepsActivity.this, "widget's added", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void PlayVideo(Uri uri) {
        if (Player == null) {
            TrackSelector selector = new DefaultTrackSelector();
            LoadControl control = new DefaultLoadControl();
            Player = ExoPlayerFactory.newSimpleInstance(this, selector, control);
            video_steps.setPlayer(Player);
            String user = Util.getUserAgent(this, "ThirdProject");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(this, user), new DefaultExtractorsFactory()
                    , null, null);

            Player.prepare(mediaSource);
            Player.setPlayWhenReady(true);
        }
    }

    void stopPlayer() {
        if (Player != null) {

            Player.stop();
            Player.release();
            Player = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopPlayer();
    }
}
