// Created by rajaraman on Jan 01, 2015
package com.rajaraman.playerprofile.network.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class BatFieldAvg implements Parcelable {

    private BatFieldMatchStatistics tests;
    private BatFieldMatchStatistics odis;

    public BatFieldMatchStatistics getTests() {
        return tests;
    }

    public void setTests(BatFieldMatchStatistics tests) {
        this.tests = tests;
    }

    public BatFieldMatchStatistics getOdis() {
        return odis;
    }

    public void setOdis(BatFieldMatchStatistics odis) {
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

    public static final Creator<BatFieldAvg> CREATOR = new Creator<BatFieldAvg>() {
        @Override
        public BatFieldAvg createFromParcel(Parcel source) {
            BatFieldAvg batFieldAvg = new BatFieldAvg();

            ClassLoader classLoader = BatFieldMatchStatistics.class.getClassLoader();

            BatFieldMatchStatistics tests =
                    (BatFieldMatchStatistics)source.readParcelable(classLoader);
            batFieldAvg.setTests(tests);

            BatFieldMatchStatistics odis =
                    (BatFieldMatchStatistics)source.readParcelable(classLoader);
            batFieldAvg.setOdis(odis);

            return batFieldAvg;
        }

        @Override
        public BatFieldAvg[] newArray(int size) {
            return new BatFieldAvg[size];
        }
    };
}