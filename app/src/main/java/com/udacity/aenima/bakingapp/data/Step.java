package com.udacity.aenima.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marina on 25/03/2018.
 */

class Step implements Parcelable{
    private final String ID_JSON_HEADER                 = "id";
    private final String SHORT_DESCRIPTION_JSON_HEADER  = "shortDescription";
    private final String DESCRIPTION_JSON_HEADER        = "description";
    private final String VIDEO_URL_JSON_HEADER          = "videoURL";
    private final String THUMBNAIL_URL_JSON_HEADER      = "thumbnailURL";


    @SerializedName(ID_JSON_HEADER)
    public long id;
    @SerializedName(SHORT_DESCRIPTION_JSON_HEADER)
    public String shortDescription;
    @SerializedName(DESCRIPTION_JSON_HEADER)
    public String description;
    @SerializedName(VIDEO_URL_JSON_HEADER)
    public String videoUrl;
    @SerializedName(THUMBNAIL_URL_JSON_HEADER)
    public String thumbnailUrl;

    public static Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel parcel) {
            String shortDescription = parcel.readString();
            String description = parcel.readString();
            String videoUrl = parcel.readString();
            String thumbnailUrl = parcel.readString();

            return new Step(shortDescription, description, videoUrl, thumbnailUrl);
        }

        @Override
        public Step[] newArray(int i) {
            return new Step[0];
        }
    };

    public Step(String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.shortDescription);
        parcel.writeString(this.description);
        parcel.writeString(this.videoUrl);
        parcel.writeString(this.thumbnailUrl);
    }
}
