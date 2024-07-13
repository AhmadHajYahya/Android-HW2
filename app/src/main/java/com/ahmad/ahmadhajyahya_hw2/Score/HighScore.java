package com.ahmad.ahmadhajyahya_hw2.Score;

public class HighScore {
    private double lat;
    private double lon;
    private int score;

    public HighScore( int score,double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
        this.score = score;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
