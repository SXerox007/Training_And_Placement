package com.skeleton.model.raman;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mcgreen on 7/10/17.
 */

public class DataObj {
    @SerializedName("statusCode")
    @Expose
    public int statusCode;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public List<Detail> data = null;

    public List<Detail> getData() {
        return data;
    }
}


