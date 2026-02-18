package com.example.smd_a1;

public class Workout {
    String title;
    String details;
    int imageRes;

    String video_link;

    Workout(String title, String details, int imageRes, String video_link) {
        this.title = title;
        this.details = details;
        this.imageRes = imageRes;
        this.video_link = video_link;
    }

    public String getDetails() {
        return details;
    }

    public String getTitle() {
        return title;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getVideoLink() {
        return video_link;
    }
}
