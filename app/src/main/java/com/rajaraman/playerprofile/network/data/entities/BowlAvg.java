// Created by rajaraman on Jan 01, 2015
package com.rajaraman.playerprofile.network.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class BowlAvg implements Parcelable {

    private BowlMatchStatistics tests;
    private BowlMatchStatistics odis;

    public BowlMatchStatistics getTests() {
        return tests;
    }

    public void setTests(BowlMatchStatistics tests) {
        this.tests = tests;
    }

    public BowlMatchStatistics getOdis() {
        return odis;
    }

    public void setOdis(BowlMatchStatistics odis) {
        this.odis = odis;
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
        dest.writeParcelable(this.tests, flags);
        dest.writeParcelable(this.odis, flags);
    }

    public static final Creator<BowlAvg> CREATOR = new Creator<BowlAvg>() {
        @Override
        public BowlAvg createFromParcel(Parcel source) {
            BowlAvg bowlAvg = new BowlAvg();

            ClassLoader classLoader = BowlMatchStatistics.class.getClassLoader();

            BowlMatchStatistics tests =
                    (BowlMatchStatistics)source.readParcelable(classLoader);
            bowlAvg.setTests(tests);

            BowlMatchStatistics odis =
                    (BowlMatchStatistics)source.readParcelable(classLoader);
            bowlAvg.setOdis(odis);

            return bowlAvg;
        }

        @Override
        public BowlAvg[] newArray(int size) {
            return new BowlAvg[size];
        }
    };
}