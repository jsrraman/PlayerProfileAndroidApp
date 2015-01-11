// Created by rajaraman on Dec 28, 2014
package com.rajaraman.playerprofile.ui.adapters;

import android.content.Context;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.rajaraman.playerprofile.R;
import com.rajaraman.playerprofile.network.data.entities.PlayerEntity;
import com.rajaraman.playerprofile.network.data.provider.VolleySingleton;

import java.util.ArrayList;

public class PlayerListAdapter extends ArrayAdapter<PlayerEntity> {
    private Context context;
    private ArrayList<PlayerEntity> playerEntityList = null;
    private ImageLoader imageLoader;

    public PlayerListAdapter(Context context, ArrayList<PlayerEntity> playerEntityList) {
        super(context, R.layout.fragment_playerlist_list, playerEntityList);
        this.context = context;
        this.playerEntityList = playerEntityList;
        this.imageLoader = VolleySingleton.getInstance().getImageLoader();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Try getting the reference to the inflated view from convertView, if available
        View rowView = convertView;

        // If rowView is null, use a view holder to store the reference of its internal views, so that
        // it can be reused later (view holder design pattern)
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = inflater.inflate(R.layout.fragment_playerlist_list, parent, false);

            // Configure view holder
            ViewHolder viewHolder = new ViewHolder();

            // Get the thumbnail url from network using NetworkImageView
            viewHolder.imageView = (NetworkImageView)
                    rowView.findViewById(R.id.fragment_playerlist_list_icon_player);

            // Set the default thumbnail image
            // Note: Set the default image only once, if you do it every time, there seems
            // to caching issues with volley library and thumbnail images are getting filled
            // randomly for other rows where thumbnails are empty.
            viewHolder.imageView.setDefaultImageResId(R.drawable.ic_launcher);

            viewHolder.textView = (TextView) rowView.
                    findViewById(R.id.fragment_playerlist_list_textview_player_name);

            rowView.setTag(viewHolder);
        }

        // Fill data
        ViewHolder viewHolder = (ViewHolder) rowView.getTag();

        // Set the thumbnail url
        String thumbnailUrl = this.playerEntityList.get(position).getThumbnailUrl();
        viewHolder.imageView.setImageUrl(thumbnailUrl, this.imageLoader);

        // Set the country name
        viewHolder.textView.setText(playerEntityList.get(position).getName());

        return rowView;
    }

    static class ViewHolder {
        public NetworkImageView imageView;
        public TextView textView;
    }
}