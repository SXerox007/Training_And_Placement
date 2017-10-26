package com.skeleton.model.raman;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mcgreen on 7/10/17.
 */

public class Detail {

    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("updatedAt")
    @Expose
    public String updatedAt;
    @SerializedName("createdAt")
    @Expose
    public String createdAt;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("uniqueId")
    @Expose
    public int uniqueId;
    @SerializedName("startDate")
    @Expose
    public String startDate;
    @SerializedName("__v")
    @Expose
    public int v;

    public String getId() {
        return id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public String getStartDate() {
        return startDate;
    }

    public int getV() {
        return v;
    }
}
