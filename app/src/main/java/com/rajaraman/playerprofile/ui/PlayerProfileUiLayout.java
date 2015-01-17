package com.rajaraman.playerprofile.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.rajaraman.playerprofile.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by rajaraman on 16/01/15.
 */
public class PlayerProfileUiLayout {

    Activity activity;

    public void constructUi(JSONObject jsonObject, Activity activity) {

        this.activity = activity;

        LinearLayout parentContainerLayout = (LinearLayout) getParentContainerLayout();

        Iterator<String> iterator = jsonObject.keys();

        while (iterator.hasNext()) {
            String key = iterator.next();

            try {
                Object value = jsonObject.get(key);

                if (value instanceof String) {
                    addNameValuePairToUiLayout(parentContainerLayout, key, (String) value);
                } else if (value instanceof JSONObject) {
                    addJsonObjectToUiLayout(parentContainerLayout, key, (JSONObject) value);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Add a scroll to our layout
        ScrollView scrollView = new ScrollView(this.activity);
        scrollView.addView(parentContainerLayout);

        // Set the activity content
        activity.setContentView(scrollView);
    }

    private ViewGroup getParentContainerLayout() {
        // Create a linear layout
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        // Configuring the width and height of the parent layout.
        LinearLayout.LayoutParams parentLayoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(parentLayoutParams);

        return linearLayout;
    }

    private void addNameValuePairToUiLayout(ViewGroup parentContainerLayout, String key, String value) {

        // Create a linear layout with horizontal orientation
        LinearLayout containerLayout = new LinearLayout(this.activity);
        containerLayout.setOrientation(LinearLayout.HORIZONTAL);

        // Add the key, value pairs and contain them in this linear layout
        containerLayout.addView(constructKeyTextView(key));
        containerLayout.addView(constructValueTextView(value));

        parentContainerLayout.addView(containerLayout);
    }

    private TextView constructKeyTextView(String text) {

        int layoutWidth = 160; // in dps
        int layoutHeight = LinearLayout.LayoutParams.WRAP_CONTENT;

        int padding = 8; // in dps

        TextView textView = constructTextView(layoutWidth, layoutHeight,
                padding, padding, padding, padding);

        // Make the text bold
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, spannableString.length(), 0);

        textView.setText(spannableString);

        return textView;
    }

    private TextView constructValueTextView(String text) {

        int layoutWidth = LinearLayout.LayoutParams.WRAP_CONTENT;
        int layoutHeight = LinearLayout.LayoutParams.WRAP_CONTENT;

        int padding = 8; // in dps

        TextView textView = constructTextView(layoutWidth, layoutHeight,
                padding, padding, padding, padding);

        textView.setText(text);

        return textView;
    }

    private TextView constructTextView(int layoutWidth, int layoutHeight, int paddingLeft, int paddingTop,
                                       int paddingRight, int paddingBottom) {

        TextView textView = new TextView(this.activity);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(layoutWidth, layoutHeight);

        textView.setLayoutParams(layoutParams);
        textView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        textView.setTextSize(12.0f);

        return textView;
    }

    private void addJsonObjectToUiLayout(ViewGroup parentContainerLayout, String key, final JSONObject value) {
        // Create a linear layout with horizontal orientation
        LinearLayout containerLayout = new LinearLayout(this.activity);
        containerLayout.setOrientation(LinearLayout.HORIZONTAL);

        // Add the key text view
        containerLayout.addView(constructKeyTextView(key));

        // Each button represents a JSONObject, so clicking it should create another activity with the dynamic linear layout
        Button button = constructButton(this.activity.getString(R.string.view));

        // Set the on click listener to invoke PlayerProfileDynamicUiActivity which builds a UI based on key, value pairs
        // If the key is another JSON object it will it recursively build another PlayerProfileDynamicUiActivity. This keeps going ...
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Activity parentActivity = (Activity) v.getContext();

                Intent intent = new Intent(parentActivity, PlayerProfileDynamicUiActivity.class);

                // Pass the JSONObject as string
                intent.putExtra("jsonObject", value.toString());

                parentActivity.startActivity(intent);
            }
        });

        // Add the button to this linear layout
        containerLayout.addView(button);

        // Add this liner layout to the parent container
        parentContainerLayout.addView(containerLayout);
    }

    private Button constructButton(String text) {

        int layoutWidth = LinearLayout.LayoutParams.WRAP_CONTENT;
        int layoutHeight = LinearLayout.LayoutParams.WRAP_CONTENT;

        int padding = 8; // in dps

        Button button = new Button(this.activity);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(layoutWidth, layoutHeight);

        button.setLayoutParams(layoutParams);
        button.setPadding(padding, padding, padding, padding);
        button.setText(text);
        button.setTextSize(12.0f);

        return button;
    }
}
