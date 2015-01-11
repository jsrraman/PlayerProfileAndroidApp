// Created by rajaraman on Dec 28, 2014
package com.rajaraman.playerprofile.ui.adapters;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.rajaraman.playerprofile.R;
import com.rajaraman.playerprofile.network.data.entities.CountryEntity;
import com.rajaraman.playerprofile.network.data.provider.VolleySingleton;

import java.util.ArrayList;

public class CountryListAdapter extends ArrayAdapter<CountryEntity> {
    private Context context;
    private ArrayList<CountryEntity> countryEntityList = null;
    private ImageLoader imageLoader;

    public CountryListAdapter(Context context, ArrayList<CountryEntity> countryEntityList) {
        super(context, R.layout.fragment_countrylist_list, countryEntityList);
        this.context = context;
        this.countryEntityList = countryEntityList;
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

            rowView = inflater.inflate(R.layout.fragment_countrylist_list, parent, false);

            // Configure view holder
            ViewHolder viewHolder = new ViewHolder();

            // Get the thumbnail url from network using NetworkImageView
            viewHolder.imageView = (NetworkImageView)
                    rowView.findViewById(R.id.fragment_countrylist_list_icon_country);

            viewHolder.textView = (TextView) rowView.
                    findViewById(R.id.fragment_countrylist_list_textview_country_name);

            rowView.setTag(viewHolder);
        }

        // Fill data
        ViewHolder viewHolder = (ViewHolder) rowView.getTag();

        // Set the thumbnail url
        viewHolder.imageView.setImageUrl(countryEntityList.get(position).getThumbnailUrl(),
                                                                              this.imageLoader);

        // Set the country name
        viewHolder.textView.setText(countryEntityList.get(position).getName());

        return rowView;
    }

    static class ViewHolder {
        public NetworkImageView imageView;
        public TextView textView;
    }
}