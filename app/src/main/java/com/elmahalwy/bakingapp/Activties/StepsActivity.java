package com.elmahalwy.bakingapp.Activties;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elmahalwy.bakingapp.R;
import com.elmahalwy.bakingapp.Utils.Constants;
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
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsActivity extends AppCompatActivity {
    @BindView(R.id.video_steps)
    SimpleExoPlayerView mVideoPlayer;
    @BindView(R.id.tv_no_video)
    TextView mNoVideo;
    @BindView(R.id.tv_step_description)
    TextView mDescription;
    @BindView(R.id.iv_thumb)
    ImageView iv_thumb;

    private SimpleExoPlayer Player;
    private String description, video, thumbnail;
    long position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        ButterKnife.bind(this);
        position = C.TIME_UNSET;

        if (savedInstanceState != null) {
            description = savedInstanceState.getString(Constants.DESCRIPTION);
            video = savedInstanceState.getString(Constants.VIDEO);
            position = savedInstanceState.getLong(Constants.VIDEO_STATE);
            thumbnail = savedInstanceState.getString(Constants.THUMBNAIL);


        } else {
            Intent intent = this.getIntent();
            if (intent != null) {
                description = intent.getStringExtra(Constants.DESCRIPTION);
                video = intent.getStringExtra(Constants.VIDEO);
                thumbnail = intent.getStringExtra(Constants.THUMBNAIL);
            }
        }
        if (!(thumbnail == null || TextUtils.isEmpty(thumbnail))) {
            imageLoader(thumbnail);
        }

        Log.e("th", "**" + thumbnail);
        InitUI();
    }
void InitUI(){
    if (description != null) {
        mDescription.setText(description);
    }
    if (video != null) {
        if (TextUtils.isEmpty(video) || video.isEmpty()) {
            mNoVideo.setText("No Video");
            mNoVideo.setVisibility(View.VISIBLE);
            mVideoPlayer.setVisibility(View.GONE);
        } else {
            mVideoPlayer.setVisibility(View.VISIBLE);
            mNoVideo.setVisibility(View.GONE);
            PlayVideo(Uri.parse(video));
        }
    }
}


    void PlayVideo(Uri uri) {
        if (Player == null) {
            TrackSelector selector = new DefaultTrackSelector();
            LoadControl control = new DefaultLoadControl();
            Player = ExoPlayerFactory.newSimpleInstance(this, selector, control);
            mVideoPlayer.setPlayer(Player);

            String user = Util.getUserAgent(this, "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(this, user), new DefaultExtractorsFactory()
                    , null, null);
            if (position != C.TIME_UNSET)
                Player.seekTo(position);


            Player.prepare(mediaSource);
            Player.setPlayWhenReady(true);
        }
    }

    void stopPlayer() {
        if (Player != null) {
            position = Player.getCurrentPosition();
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.DESCRIPTION, description);
        outState.putString(Constants.VIDEO, video);
        outState.putLong(Constants.VIDEO_STATE, C.TIME_UNSET);
        outState.putString(Constants.THUMBNAIL, thumbnail);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        description = savedInstanceState.getString(Constants.DESCRIPTION);
        video = savedInstanceState.getString(Constants.VIDEO);
        position = savedInstanceState.getLong(Constants.VIDEO_STATE);
        thumbnail = savedInstanceState.getString(Constants.THUMBNAIL);
    }


    private void imageLoader(String url) {
        try {
            Picasso.with(this)
                    .load(url)
                    .error(R.drawable.bake_logo)
                    .into(iv_thumb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!(video == null || TextUtils.isEmpty(video))) {
            PlayVideo(Uri.parse(video));
        }
    }
}
