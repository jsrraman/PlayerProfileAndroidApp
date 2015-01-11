// Created by rajaraman on Dec 26, 2014

package com.rajaraman.playerprofile.network.data.provider;

import android.os.Parcel;
import android.os.Parcelable;

public class ApiReqResData implements Parcelable {
    private int requestWebServiceApiId;
    private String requestUrl;

    // Important Note:
    // We don't need to parcel(mashal)/unparcel (unmarshal) this, as it is just going to carry the
    // response object (web service response) from the intent service. If we need to set this from
    // client (caller) we would have needed to parcel/unparcel this so we will ignore this member in
    // writeToParcel and Parcelable.Creator methods
    private Object responseDataObj;

    public ApiReqResData() {}

    public int getRequestWebServiceApiId() {
        return requestWebServiceApiId;
    }

    public void setRequestWebServiceApiId(int requestWebServiceApiId) {
        this.requestWebServiceApiId = requestWebServiceApiId;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Object getResponseData() {
        return responseDataObj;
    }

    public void setResponseData(Object responseDataObj) {
        this.responseDataObj = responseDataObj;
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
        // Order is important, first request webservice name, requested url
        dest.writeInt(this.requestWebServiceApiId);
        dest.writeString(this.requestUrl);

        // Remember we don't need something like below.
        // Please check the note for this member variable
        // dest.writeValue(this.responseData);
    }

    public static final Creator<ApiReqResData> CREATOR = new Creator<ApiReqResData>() {

        @Override
        public ApiReqResData createFromParcel(Parcel source) {
            ApiReqResData apiReqResData = new ApiReqResData();

            apiReqResData.setRequestWebServiceApiId(source.readInt());
            apiReqResData.setRequestUrl(source.readString());

            return apiReqResData;
        }

        @Override
        public ApiReqResData[] newArray(int size) {
            return new ApiReqResData[size];
        }
    };

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
}