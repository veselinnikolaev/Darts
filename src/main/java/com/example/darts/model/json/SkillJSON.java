package com.example.darts.model.json;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class SkillJSON {
    private String name;
    @SerializedName("photo_url")
    private String photoUrl;
}
