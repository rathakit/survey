package com.nimbl3.survey.models;

/**
 * Created by rathakit.nop on 9/22/2016 AD.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nimbl3.survey.utilities.Constant;

/**
 * The Survey Class
 */
public class Survey {

    // The name
    @SerializedName("title")
    @Expose
    private String name = Constant.EMPTY_STRING;

    // The description
    @SerializedName("description")
    @Expose
    private String description = Constant.EMPTY_STRING;

    // The cover image URL displayed as a background.
    @SerializedName("cover_image_url")
    @Expose
    private String coverImageUrl = Constant.EMPTY_STRING;

    // TODO GETTER/SETTER
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    /**
     * TODO Public Methods
     * Gets the high resolution image url.
     * @return
     */
    public String getHighResolutionImageUrl() {
        return coverImageUrl + Constant.HIGH_RESOLUTION_IMAGE_APPEND_CHAR;
    }
}
