// Created by rajaraman on Jan 01, 2015
package com.rajaraman.playerprofile.network.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class BatFieldMatchStatistics implements Parcelable {
    private String matches;
    private String runs;
    private String highest;
    private String average;
    private String hundreds;
    private String fifties;

    public String getMatches() {
        return matches;
    }

    public void setMatches(String matches) {
        this.matches = matches;
    }

    public String getRuns() {
        return runs;
    }

    public void setRuns(String runs) {
        this.runs = runs;
    }

    public String getHighest() {
        return highest;
    }

    public void setHighest(String highest) {
        this.highest = highest;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getHundreds() {
        return hundreds;
    }

    public void setHundreds(String hundreds) {
        this.hundreds = hundreds;
    }

    public String getFifties() {
        return fifties;
    }

    public void setFifties(String fifties) {
        this.fifties = fifties;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.matches);
        dest.writeString(this.runs);
        dest.writeString(this.highest);
        dest.writeString(this.average);
        dest.writeString(this.hundreds);
        dest.writeString(this.fifties);
    }

    public static final Creator<BatFieldMatchStatistics> CREATOR =
                                                        new Creator<BatFieldMatchStatistics>() {

        @Override
        public BatFieldMatchStatistics createFromParcel(Parcel source) {
            BatFieldMatchStatistics batFieldMatchStatistics = new BatFieldMatchStatistics();

            batFieldMatchStatistics.setMatches(source.readString());
            batFieldMatchStatistics.setRuns(source.readString());
            batFieldMatchStatistics.setHighest(source.readString());
            batFieldMatchStatistics.setAverage(source.readString());
            batFieldMatchStatistics.setHundreds(source.readString());
            batFieldMatchStatistics.setFifties(source.readString());

            return batFieldMatchStatistics;
        }

        @Override
        public BatFieldMatchStatistics[] newArray(int size) {
            return new BatFieldMatchStatistics[size];
        }
    };
}