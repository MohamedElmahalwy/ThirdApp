package com.elmahalwy.bakingapp.Frgments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.elmahalwy.bakingapp.R;
import com.elmahalwy.bakingapp.Utils.Constants;
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


public class StepDetailsFragment extends Fragment {


    @BindView(R.id.step_video_player)
    SimpleExoPlayerView mVideoPlayer;
    @BindView(R.id.step_video)
    TextView mStepVideo;
    @BindView(R.id.step_description)
    TextView mStepDescription;

    private SimpleExoPlayer mPlayer;
    private String description, video;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            description = savedInstanceState.getString(Constants.DESCRIPTION);
            video = savedInstanceState.getString(Constants.VIDEO);
        }
        setupUi();

        return view;
    }

    private void setupUi() {
        if (description != null) {
            mStepDescription.setText(description);
        }
        if (video != null) {
            if (TextUtils.isEmpty(video) || video.isEmpty()) {
                mStepVideo.setText("No Video");
                mStepVideo.setVisibility(View.VISIBLE);
                mVideoPlayer.setVisibility(View.INVISIBLE);
            } else {
                mVideoPlayer.setVisibility(View.VISIBLE);
                mStepVideo.setVisibility(View.GONE);
                setupMediaPlayer(Uri.parse(video));
            }
        } else {
            mStepVideo.setText("No Video");
            mStepVideo.setVisibility(View.VISIBLE);
            mVideoPlayer.setVisibility(View.GONE);
        }

    }

    private void setupMediaPlayer(Uri uri) {
        if (mPlayer == null) {
            TrackSelector selector = new DefaultTrackSelector();
            LoadControl control = new DefaultLoadControl();
            mPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), selector, control);
            mVideoPlayer.setPlayer(mPlayer);

            String user = Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(getActivity(), user), new DefaultExtractorsFactory()
                    , null, null);
            mPlayer.prepare(mediaSource);
            mPlayer.setPlayWhenReady(true);
        }
    }

    private void stopPlayer() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        stopPlayer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.VIDEO, video);
        outState.putString(Constants.DESCRIPTION, description);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        try {
            savedInstanceState.getLong(Constants.VIDEO_STATE);
            savedInstanceState.getString(Constants.VIDEO);
            savedInstanceState.getString(Constants.THUMBNAIL);
            savedInstanceState.getString(Constants.DESCRIPTION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
