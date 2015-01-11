// Created by rajaraman on Dec 25, 2014

package com.rajaraman.playerprofile.network.data.entities;

public class PlayerEntity {
    private int countryId;
    private String playerUrl;
    private String thumbnailUrl;
    private int playerId;
    private String name;
    private String country;
    private String age;
    private String battingStyle;
    private String bowlingStyle;
    private BatFieldAvg batFieldAvg;
    private BowlAvg bowlAvg;

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getPlayerUrl() {
        return playerUrl;
    }

    public void setPlayerUrl(String playerUrl) {
        this.playerUrl = playerUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBattingStyle() {
        return battingStyle;
    }

    public void setBattingStyle(String battingStyle) {
        this.battingStyle = battingStyle;
    }

    public String getBowlingStyle() {
        return bowlingStyle;
    }

    public void setBowlingStyle(String bowlingStyle) {
        this.bowlingStyle = bowlingStyle;
    }

    public BatFieldAvg getBatFieldAvg() {
        return batFieldAvg;
    }

    public void setBatFieldAvg(BatFieldAvg batFieldAvg) {
        this.batFieldAvg = batFieldAvg;
    }

    public BowlAvg getBowlAvg() {
        return bowlAvg;
    }

    public void setBowlAvg(BowlAvg bowlAvg) {
        this.bowlAvg = bowlAvg;
    }

    public PlayerEntity() {}

    public PlayerEntity(int countryId, String playerUrl, String thumbnailUrl, int playerId,
                        String name, String country, String age, String battingStyle,
                        String bowlingStyle, BatFieldAvg batFieldAvg, BowlAvg bowlAvg) {
        this.countryId = countryId;
        this.playerUrl = playerUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.playerId = playerId;
        this.name = name;
        this.country = country;
        this.age = age;
        this.battingStyle = battingStyle;
        this.bowlingStyle = bowlingStyle;
        this.batFieldAvg = batFieldAvg;
        this.bowlAvg = bowlAvg;
    }
}