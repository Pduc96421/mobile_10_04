package com.tren_lop.bai1003.models;

import java.io.Serializable;

public class Movie implements Serializable {
    private String id;
    private String title;
    private String description;
    private String posterUrl;
    private String trailerUrl;
    private int duration; // in minutes
    private String releaseDate;

    public Movie() {
    }

    public Movie(String id, String title, String description, String posterUrl, String trailerUrl, int duration, String releaseDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.posterUrl = posterUrl;
        this.trailerUrl = trailerUrl;
        this.duration = duration;
        this.releaseDate = releaseDate;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }
    public String getTrailerUrl() { return trailerUrl; }
    public void setTrailerUrl(String trailerUrl) { this.trailerUrl = trailerUrl; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
}
