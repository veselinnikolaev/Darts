package com.example.darts.model.json;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class LocationJSON {
    private String city;
    @SerializedName("lat")
    private Double latitude;
    @SerializedName("lng")
    private Double longitude;
    @SerializedName("admin_name")
    private String region;
}
