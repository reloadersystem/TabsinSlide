package com.example.sistem03user.youtubeapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;

public class VideoPlayerArrayAdapter extends ArrayAdapter<VideoPlayerItem> {
    private final ArrayList<VideoPlayerItem> videoList;

    public VideoPlayerArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<VideoPlayerItem> objects) {
        super(context, resource, objects);
        this.videoList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        YouTubeThumbnailView.OnInitializedListener initializer;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.item_video_list, null);

        final VideoPlayerItem curItem = this.videoList.get(position);

        TextView tvTitle = view.findViewById(R.id.thumbnailTitle);
        tvTitle.setText(curItem.getTitle());

        YouTubeThumbnailView thumbnailView = view.findViewById(R.id.thumbnailView);
        initializer = new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(curItem.getId());

            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
       // thumbnailView.initialize(PackageTools.getYoutubeAPIKey(), initializer);
        return view;

    }
}