// Created by rajaraman on Jan 01, 2015
package com.rajaraman.playerprofile.network.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class BowlMatchStatistics implements Parcelable {
    private String matches;
    private String wickets;
    private String bestMatchBowling;
    private String average;
    private String fourWktsInInns;
    private String fiveWktsInInns;
    private String tenWktsInMatch;

    public String getMatches() {
        return matches;
    }

    public void setMatches(String matches) {
        this.matches = matches;
    }

    public String getWickets() {
        return wickets;
    }

    public void setWickets(String wickets) {
        this.wickets = wickets;
    }

    public String getBestMatchBowling() {
        return bestMatchBowling;
    }

    public void setBestMatchBowling(String bestMatchBowling) {
        this.bestMatchBowling = bestMatchBowling;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getFourWktsInInns() {
        return fourWktsInInns;
    }

    public void setFourWktsInInns(String fourWktsInInns) {
        this.fourWktsInInns = fourWktsInInns;
    }

    public String getFiveWktsInInns() {
        return fiveWktsInInns;
    }

    public void setFiveWktsInInns(String fiveWktsInInns) {
        this.fiveWktsInInns = fiveWktsInInns;
    }

    public String getTenWktsInMatch() {
        return tenWktsInMatch;
    }

    public void setTenWktsInMatch(String tenWktsInMatch) {
        this.tenWktsInMatch = tenWktsInMatch;
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
        dest.writeString(this.wickets);
        dest.writeString(this.bestMatchBowling);
        dest.writeString(this.average);
        dest.writeString(this.fourWktsInInns);
        dest.writeString(this.fiveWktsInInns);
        dest.writeString(this.tenWktsInMatch);
    }

    public static final Creator<BowlMatchStatistics> CREATOR =
                                                        new Creator<BowlMatchStatistics>() {

        @Override
        public BowlMatchStatistics createFromParcel(Parcel source) {
            BowlMatchStatistics bowlMatchStatistics = new BowlMatchStatistics();

            bowlMatchStatistics.setMatches(source.readString());
            bowlMatchStatistics.setWickets(source.readString());
            bowlMatchStatistics.setBestMatchBowling(source.readString());
            bowlMatchStatistics.setAverage(source.readString());
            bowlMatchStatistics.setFourWktsInInns(source.readString());
            bowlMatchStatistics.setFiveWktsInInns(source.readString());
            bowlMatchStatistics.setTenWktsInMatch(source.readString());

            return bowlMatchStatistics;
        }

        @Override
        public BowlMatchStatistics[] newArray(int size) {
            return new BowlMatchStatistics[size];
        }
    };
}