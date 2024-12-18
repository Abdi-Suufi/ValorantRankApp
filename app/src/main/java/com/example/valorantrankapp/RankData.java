package com.example.valorantrankapp;

public class RankData {
    private String current_rank;
    private String current_rank_image;
    private String current_elo;
    private String highest_rank;
    private String highest_rank_image;
    private String highest_rank_season;

    // Getters and setters for all fields
    public String getCurrent_rank() {
        return current_rank;
    }

    public void setCurrent_rank(String current_rank) {
        this.current_rank = current_rank;
    }

    public String getCurrent_rank_image() {
        return current_rank_image;
    }

    public void setCurrent_rank_image(String current_rank_image) {
        this.current_rank_image = current_rank_image;
    }

    public String getCurrent_elo() {
        return current_elo;
    }

    public void setCurrent_elo(String current_elo) {
        this.current_elo = current_elo;
    }

    public String getHighest_rank() {
        return highest_rank;
    }

    public void setHighest_rank(String highest_rank) {
        this.highest_rank = highest_rank;
    }

    public String getHighest_rank_image() {
        return highest_rank_image;
    }

    public void setHighest_rank_image(String highest_rank_image) {
        this.highest_rank_image = highest_rank_image;
    }

    public String getHighest_rank_season() {
        return highest_rank_season;
    }

    public void setHighest_rank_season(String highest_rank_season) {
        this.highest_rank_season = highest_rank_season;
    }
}