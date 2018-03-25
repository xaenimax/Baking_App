package com.udacity.aenima.bakingapp.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marina on 25/03/2018.
 */

class Step {
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
}
