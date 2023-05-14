package com.example.obstacleracegame.Models;

import com.google.android.material.imageview.ShapeableImageView;

public class Record implements Comparable<Record> {

    private String title = "SCORE";
    private String score = "";
    private ShapeableImageView cup;
    private double latitude;
    private double longitude;

    public Record() {
    }

    public ShapeableImageView getIMG() {
        return cup;
    }

    public String getTitle() {
        return title;
    }

    public String getScore() {
        return score;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Record setIMG(ShapeableImageView IMG) {
        this.cup = cup;
        return this;
    }

    public Record setTitle(String title) {
        this.title = title;
        return this;
    }

    public Record setScore(String score) {
        this.score = score;
        return this;
    }

    public Record setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Record setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public String toString() {
        return "Records{" +
                "title='" + title + '\'' +
                ", score='" + score + '\'' +
                ", image='" + cup + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }

    @Override
    public int compareTo(Record record) {
        Integer score1 = Integer.parseInt(this.score);
        Integer score2 = Integer.parseInt(record.score);
        return score2.compareTo(score1);
    }
}
