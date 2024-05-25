package com.example.darts.model.json;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class JobApplicationData {
    @SerializedName("data")
    private List<JobApplicationJSON> jobApplicationJSONs;
}
